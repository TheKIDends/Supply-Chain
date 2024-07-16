package com.blockchain.supplychain.service;

import com.blockchain.supplychain.chaincode.Config;
import com.blockchain.supplychain.chaincode.client.RegisterUserHyperledger;
import com.blockchain.supplychain.chaincode.util.ConnectionParamsUtil;
import com.blockchain.supplychain.chaincode.util.WalletUtil;
import com.blockchain.supplychain.entity.ProductLicense;
import com.blockchain.supplychain.model.User;
import com.owlike.genson.Genson;
import lombok.SneakyThrows;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

@Service
public class HyperledgerService {
    private final static Logger LOG = Logger.getLogger(HyperledgerService.class.getName());
    private final Genson genson = new Genson();

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    public static void registerListener(Network network, Channel channel, Contract contract) throws InvalidArgumentException {
        Consumer<BlockEvent> e = new Consumer<BlockEvent>() {
            @SneakyThrows
            @Override
            public void accept(BlockEvent blockEvent) {
                long bN = blockEvent.getBlockNumber();
                System.out.println("network blockListener" + bN);
                for (BlockEvent.TransactionEvent transactionEvent : blockEvent.getTransactionEvents()) {
                    String mspId = transactionEvent.getCreator().getMspid();
                    String peer = transactionEvent.getPeer().getName();
                    System.out.println(String.format("[NetworkBlockEventListener] transactionEventId: %s, creatorMspId: %s, peer: %s", transactionEvent.getTransactionID(), mspId, peer));
                }
            }

            @Override
            public Consumer<BlockEvent> andThen(Consumer<? super BlockEvent> after) {
                System.out.println("done accept event op");
                return null;
            }
        };
        network.addBlockListener(e);
    }

    private Contract getContract(User user) throws Exception {
        String userWalletIdentity = user.getEmail();
        String userId = user.getId();
        String userIdentity = String.valueOf(userId);

        String org = determineOrg(user);
        Map<String, String> connectionConfigParams = ConnectionParamsUtil.setOrgConfigParams(org);
        String connectionProfilePath = connectionConfigParams.get("networkConfigPath");

        Gateway gateway = connect(userWalletIdentity, connectionProfilePath, userIdentity, org);
        Network network = gateway.getNetwork(Config.CHANNEL_NAME);
        Contract contract = network.getContract(Config.CHAINCODE_NAME);
        registerListener(network, network.getChannel(), contract);
        return contract;
    }

    private Gateway connect(String userWalletIdentity, String connectionProfilePath, String userIdentity, String org) throws Exception {
        Identity identity = RegisterUserHyperledger.enrollOrgAppUsers(userWalletIdentity, org, userIdentity);
        if (identity == null) {
            throw new Exception(String.format("Cannot find %s's idenitty", userWalletIdentity));
        }

        Gateway.Builder builder = Gateway.createBuilder();
        Path networkConfigPath = Paths.get(connectionProfilePath);
        WalletUtil walletUtil = new WalletUtil();
        builder.identity(walletUtil.getWallet(), userWalletIdentity).networkConfig(networkConfigPath).discovery(true);

        return builder.connect();
    }

    private void formatExceptionMessage(Exception e) throws Exception {
        String msg = e.getMessage();
        String errorMsg = msg.substring(msg.lastIndexOf(":") + 1);
        throw new Exception(errorMsg);
    }

    private String determineOrg(User user) {
        return Config.ORG1;
    }

    public ProductLicense sendProductLicense(User user, JSONObject jsonObject) throws Exception {
        ProductLicense productLicense = null;
        try {
            Contract contract = getContract(user);

            byte[] result = contract.submitTransaction(
                    "sendProductLicense",
                    jsonObject.toString()
            );

            String appointmentRequestStr = new String(result);
            productLicense = genson.deserialize(appointmentRequestStr, ProductLicense.class);
            LOG.info("result: " + productLicense);
        } catch (Exception e) {
            formatExceptionMessage(e);
        }
        return productLicense;
    }

    public ProductLicense getProductLicense(User user, String requestId) throws Exception {
        ProductLicense productLicense = new ProductLicense();
        try {
            Contract contract = getContract(user);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("requestId", requestId);

            byte[] result = contract.evaluateTransaction(
                    "getProductLicense",
                    jsonObject.toString()
            );

            String productLicenseStr = new String(result);
            productLicense = genson.deserialize(productLicenseStr, ProductLicense.class);

            LOG.info("productLicense: " + productLicense);
        } catch (Exception e) {
            formatExceptionMessage(e);
        }
        return productLicense;
    }
}
