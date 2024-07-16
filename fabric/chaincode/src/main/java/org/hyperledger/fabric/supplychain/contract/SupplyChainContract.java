package org.hyperledger.fabric.supplychain.contract;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import org.hyperledger.fabric.supplychain.entity.Product;
import org.hyperledger.fabric.supplychain.entity.ProductLicense;
import org.hyperledger.fabric.supplychain.enumeration.ContractErrors;
import org.hyperledger.fabric.supplychain.enumeration.RequestStatus;
import org.hyperledger.fabric.supplychain.enumeration.RequestType;
import org.json.JSONObject;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Contract(name = "chaincode",
        info = @Info(
                title = "Supply Chain Contract",
                description = "",
                version = "0.0.1-SNAPSHOT",
                license = @License(name = "SPDX-License-Identifier: Apache-2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(email = "chaukhanh0605@gmail.com",
                        name = "Nguyen Chau Khanh", url = "https://github.com/TheKIDends")
        )
)
@Default
public class SupplyChainContract implements ContractInterface {
    private final static Logger LOG = Logger.getLogger(SupplyChainContract.class.getName());
    private Genson genson = new Genson();

    @Override
    public Context createContext(ChaincodeStub stub) {
        LOG.info("createContext()");
        return new Context(stub);
    }

    @Override
    public void beforeTransaction(Context ctx) {
        List<String> paramList = ctx.getStub().getParameters();
        String params = String.join(",", paramList);
        String function = ctx.getStub().getFunction();

        System.out.println();
        System.out.println("----------------------------------- BEGIN -----------------------------------");
        LOG.info(String.format("Function name: %s, params: [%s]", function, params));
        clientIdentityInfo(ctx);
    }

    private void clientIdentityInfo(final Context ctx) {
        try {
            String userIdentityId = ctx.getClientIdentity().getAttributeValue("userIdentityId");
            String clientIdentityId = ctx.getClientIdentity().getId();
            String clientIdentityMspId = ctx.getClientIdentity().getMSPID();
            LOG.info(String.format("clientIdentityId: %s, clientIdentityMspId: %s, userIdentityId: %s", clientIdentityId, clientIdentityMspId, userIdentityId));
        } catch (Exception e) {
            String errorMessage = "Error during method ctx.getClientIdentity.getAttributeValue()";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.UNAUTHORIZED_EDIT_ACCESS);
        }
    }

    @Override
    public void afterTransaction(Context ctx, Object result) {
        String function = ctx.getStub().getFunction();
        LOG.info(String.format("Function name: %s", function));
        if (result == null) {
            LOG.info("result is null");
        } else {
            LOG.info(String.format("object type: %s, else: ", result.getClass().getTypeName(), result.getClass().toString()));
            if (result.getClass().getTypeName().equals("java.util.ArrayList")) {
                ArrayList<String> list = (ArrayList<String>) result;
                LOG.info(String.format("arraylist size: %d", list.size()));
            }
        }
        LOG.info("----------------------------------- END -----------------------------------");
        System.out.println("-----------------------------------");
    }

    private void authorizeRequest(Context ctx, String userIdentityInDb, String methodName) {
        String userIdentityId = "";
        try {
            userIdentityId = ctx.getClientIdentity().getAttributeValue("userIdentityId");
        } catch (Exception e) {
            String errorMessage = "Error during method ctx.getClientIdentity.getAttributeValue(...)";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.UNAUTHORIZED_EDIT_ACCESS);
        }
        if (!userIdentityId.equals(userIdentityInDb)) {
            String errorMessage = String.format("Error during method: %s , identified user does not have write rights", methodName);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.UNAUTHORIZED_EDIT_ACCESS);
        }
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String sendProductLicense(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);

        String requestId = ctx.getStub().getTxId();
        String senderId = jsonObject.getString("senderId");
        String recipientId = jsonObject.getString("recipientId");
        String dateCreated = jsonObject.getString("dateCreated");
        String dateModified = jsonObject.getString("dateModified");
        String requestType = RequestType.PRODUCT_LICENSE;
        String requestStatus = RequestStatus.PENDING;
        String productId = jsonObject.getString("productId");
        String details = jsonObject.getString("details");

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(ProductLicense.class.getSimpleName(), requestId);
        String dbKey = compositeKey.toString();

        ProductLicense productLicense = new ProductLicense (
                requestId,
                senderId,
                recipientId,
                dateCreated,
                dateModified,
                requestType,
                requestStatus,
                productId,
                details
        );

        String productLicenseStr = genson.serialize(productLicense);
        ctx.getStub().putStringState(dbKey, productLicenseStr);
        return productLicenseStr;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getProductLicense(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String requestId = jsonObject.getString("requestId");

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(ProductLicense.class.getSimpleName(), requestId);
        String dbKey = compositeKey.toString();

        byte [] result = ctx.getStub().getState(dbKey);

        if (result.length > 0) {
            System.out.println(new String(result, UTF_8));

            ProductLicense productLicense = genson.deserialize(result, ProductLicense.class);
//            try {
//                authorizeRequest(ctx, viewRequest.getSenderId(), "getViewRequest(validate senderId)");
//            }
//            catch (ChaincodeException chaincodeException) {
//                try {
//                    authorizeRequest(ctx, viewRequest.getRecipientId(), "getViewRequest(validate recipientId)");
//                }
//                catch (ChaincodeException ce) {
//                    throw ce;
//                }
//            }
            System.out.println("Product License: " + productLicense);
            String productLicenseStr = genson.serialize(productLicense);
            return productLicenseStr;
        } else {
            String errorMessage = String.format("Product license %s does not exist.", requestId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.REQUEST_NOT_FOUND);
        }
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String setProductLicenseStatus(Context ctx, String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String requestId = jsonObject.getString("requestId");
        String requestStatus = jsonObject.getString("requestStatus");

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(ProductLicense.class.getSimpleName(), requestId);
        String dbKey = compositeKey.toString();

        byte [] result = ctx.getStub().getState(dbKey);

        if (result.length > 0) {
            System.out.println(new String(result, UTF_8));

            ProductLicense productLicense = genson.deserialize(result, ProductLicense.class);
            productLicense.setRequestStatus(requestStatus);

            System.out.println("Product License: " + productLicense);
            String productLicenseStr = genson.serialize(productLicense);
            ctx.getStub().putStringState(dbKey, productLicenseStr);
            return productLicenseStr;
        } else {
            String errorMessage = String.format("Product license %s does not exist.", requestId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.REQUEST_NOT_FOUND);
        }
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String addProduct(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);

        String productId = ctx.getStub().getTxId();
        String productName = jsonObject.getString("productName");
        String licenseID = jsonObject.getString("licenseID");
        String creatorId = jsonObject.getString("creatorId");
        String dateCreated = jsonObject.getString("dateCreated");
        String details = jsonObject.getString("details");

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(Product.class.getSimpleName(), productId);
        String dbKey = compositeKey.toString();

        Product product = new Product (
                productId,
                productName,
                licenseID,
                creatorId,
                dateCreated,
                details
        );

        String productStr = genson.serialize(product);
        ctx.getStub().putStringState(dbKey, productStr);
        return productStr;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getProduct(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String productId = jsonObject.getString("productId");

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(Product.class.getSimpleName(), productId);
        String dbKey = compositeKey.toString();

        byte [] result = ctx.getStub().getState(dbKey);

        if (result.length > 0) {
            System.out.println(new String(result, UTF_8));

            Product product = genson.deserialize(result, Product.class);

            System.out.println("getProduct: " + product);
            String productStr = genson.serialize(product);
            return productStr;
        } else {
            String errorMessage = String.format("Product %s does not exist.", productId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.REQUEST_NOT_FOUND);
        }
    }

}