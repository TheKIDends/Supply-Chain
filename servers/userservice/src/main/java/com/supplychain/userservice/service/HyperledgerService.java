package com.supplychain.userservice.service;

import com.owlike.genson.Genson;
import com.supplychain.userservice.chaincode.Config;
import com.supplychain.userservice.chaincode.client.RegisterUserHyperledger;
import com.supplychain.userservice.chaincode.util.ConnectionParamsUtil;
import com.supplychain.userservice.chaincode.util.WalletUtil;
import com.supplychain.userservice.data.User;
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
        String userWalletIdentity = user.getPhoneNumber();
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

    private Gateway connect(String userWalletIdentity, String connectionProfilePath, String userIdentity, String org)
            throws Exception {
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

//    public ProductLicense sendProductLicense(User user, JSONObject jsonObject) throws Exception {
//        ProductLicense productLicense = null;
//        try {
//            Contract contract = getContract(user);
//
//            byte[] result = contract.submitTransaction(
//                    "sendProductLicense",
//                    jsonObject.toString()
//            );
//
//            String appointmentRequestStr = new String(result);
//            productLicense = genson.deserialize(appointmentRequestStr, ProductLicense.class);
//            LOG.info("sendProductLicense: " + productLicense);
//        } catch (Exception e) {
//            formatExceptionMessage(e);
//        }
//        return productLicense;
//    }
//
//    public ProductLicense getProductLicense(User user, JSONObject jsonObject) throws Exception {
//        ProductLicense productLicense = new ProductLicense();
//        try {
//            Contract contract = getContract(user);
//
//            byte[] result = contract.evaluateTransaction(
//                    "getProductLicense",
//                    jsonObject.toString()
//            );
//
//            String productLicenseStr = new String(result);
//            productLicense = genson.deserialize(productLicenseStr, ProductLicense.class);
//
//            LOG.info("getProductLicense: " + productLicense);
//        } catch (Exception e) {
//            formatExceptionMessage(e);
//        }
//        return productLicense;
//    }
//
//    public ProductLicense setProductLicenseStatus(User user, JSONObject jsonObject) throws Exception {
//        ProductLicense productLicense = new ProductLicense();
//        try {
//            Contract contract = getContract(user);
//
//            byte[] result = contract.submitTransaction(
//                    "setProductLicenseStatus",
//                    jsonObject.toString()
//            );
//
//            String productLicenseStr = new String(result);
//            productLicense = genson.deserialize(productLicenseStr, ProductLicense.class);
//
//            LOG.info("setProductLicenseStatus: " + productLicense);
//        } catch (Exception e) {
//            formatExceptionMessage(e);
//        }
//        return productLicense;
//    }
//
//    public Product addProduct(User user, JSONObject jsonObject) throws Exception {
//        Product product = null;
//        try {
//            Contract contract = getContract(user);
//
//            byte[] result = contract.submitTransaction(
//                    "addProduct",
//                    jsonObject.toString()
//            );
//
//            String productStr = new String(result);
//            product = genson.deserialize(productStr, Product.class);
//
//            LOG.info("addProduct: " + product);
//        } catch (Exception e) {
//            formatExceptionMessage(e);
//        }
//        return product;
//    }
//
//    public Product getProduct(User user, JSONObject jsonObject ) throws Exception {
//        Product product = new Product();
//        try {
//
//            Contract contract = getContract(user);
//
//            byte[] result = contract.evaluateTransaction(
//                    "getProduct",
//                    jsonObject.toString()
//            );
//
//            String productStr = new String(result);
//            product = genson.deserialize(productStr, Product.class);
//
//            LOG.info("getProduct: " + product);
//        } catch (Exception e) {
//            formatExceptionMessage(e);
//        }
//        return product;
//    }
//
//    public ProductItem addItem(User user, JSONObject jsonObject) throws Exception {
//        ProductItem productItem = null;
//        try {
//            Contract contract = getContract(user);
//
//            byte[] result = contract.submitTransaction(
//                    "addItem",
//                    jsonObject.toString()
//            );
//
//            String itemStr = new String(result);
//            productItem = genson.deserialize(itemStr, ProductItem.class);
//
//            LOG.info("addItem: " + productItem);
//        } catch (Exception e) {
//            formatExceptionMessage(e);
//        }
//        return productItem;
//    }
//
//    public ProductItem setContainerIdForItem(User user, JSONObject jsonObject) throws Exception {
//        ProductItem productItem = new ProductItem();
//        try {
//            Contract contract = getContract(user);
//
//            byte[] result = contract.submitTransaction(
//                    "setContainerIdForItem",
//                    jsonObject.toString()
//            );
//
//            String itemStr = new String(result);
//            productItem = genson.deserialize(itemStr, ProductItem.class);
//
//            LOG.info("setContainerIdForItem: " + productItem);
//        } catch (Exception e) {
//            formatExceptionMessage(e);
//        }
//        return productItem;
//    }
//
//    public ProductItem removeContainerIdForItem(User user, JSONObject jsonObject) throws Exception {
//        ProductItem productItem = new ProductItem();
//        try {
//            Contract contract = getContract(user);
//
//            byte[] result = contract.submitTransaction(
//                    "removeContainerIdForItem",
//                    jsonObject.toString()
//            );
//
//            String itemStr = new String(result);
//            productItem = genson.deserialize(itemStr, ProductItem.class);
//
//            LOG.info("removeContainerIdForItem: " + productItem);
//        } catch (Exception e) {
//            formatExceptionMessage(e);
//        }
//        return productItem;
//    }
//
//    public ProductItem getItem(User user, JSONObject jsonObject) throws Exception {
//        ProductItem productItem = new ProductItem();
//        try {
//            Contract contract = getContract(user);
//
//            byte[] result = contract.submitTransaction(
//                    "getItem",
//                    jsonObject.toString()
//            );
//
//            String itemStr = new String(result);
//            productItem = genson.deserialize(itemStr, ProductItem.class);
//
//            LOG.info("getItem: " + productItem);
//        } catch (Exception e) {
//            formatExceptionMessage(e);
//        }
//        return productItem;
//    }
}
