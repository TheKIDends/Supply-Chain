package org.hyperledger.fabric.supplychain.contract;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import org.hyperledger.fabric.supplychain.entity.Item;
import org.hyperledger.fabric.supplychain.entity.Product;
import org.hyperledger.fabric.supplychain.entity.ProductLicense;
import org.hyperledger.fabric.supplychain.enumeration.ContractErrors;
import org.hyperledger.fabric.supplychain.enumeration.ItemType;
import org.hyperledger.fabric.supplychain.enumeration.RequestStatus;
import org.hyperledger.fabric.supplychain.enumeration.RequestType;
import org.hyperledger.fabric.supplychain.util.TimeUtils;
import org.json.JSONObject;

import java.util.*;
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
            throw new ChaincodeException(errorMessage, ContractErrors.UNAUTHORIZED);
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
            throw new ChaincodeException(errorMessage, ContractErrors.UNAUTHORIZED);
        }
        if (!userIdentityId.equals(userIdentityInDb)) {
            String errorMessage = String.format("Error during method: %s , identified user does not have write rights", methodName);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.UNAUTHORIZED);
        }
    }

    private boolean isProductLicenseAccepted(Context ctx, String productId) {
        if (ctx == null) return false;
        if (!isEntityExists(ctx, Product.class.getSimpleName(), productId)) return  false;

        CompositeKey productCompositeKey = ctx.getStub().createCompositeKey(Product.class.getSimpleName(), productId);
        String productDBKey = productCompositeKey.toString();
        byte [] productResult = ctx.getStub().getState(productDBKey);
        Product product = genson.deserialize(productResult, Product.class);

        String licenseID = product.getLicenseID();
        if (!isEntityExists(ctx, ProductLicense.class.getSimpleName(), licenseID)) return false;

        CompositeKey licenseCompositeKey = ctx.getStub().createCompositeKey(ProductLicense.class.getSimpleName(), licenseID);
        String licenseDBKey = licenseCompositeKey.toString();
        byte [] licenseResult = ctx.getStub().getState(licenseDBKey);
        ProductLicense license = genson.deserialize(licenseResult, ProductLicense.class);

        return Objects.equals(license.getRequestStatus(), RequestStatus.ACCEPTED);
    }

    private boolean isEntityExists(Context ctx, String classSimpleName, String entityId) {
        if (ctx == null) return false;
        CompositeKey compositeKey = ctx.getStub().createCompositeKey(classSimpleName, entityId);
        String dbKey = compositeKey.toString();
        byte [] result = ctx.getStub().getState(dbKey);
        return result.length > 0;
    }

    public boolean hasCycleOnContainerAddition(Context ctx, String itemId, String containerId) {
        Set<String> visited = new HashSet<>();
        visited.add(itemId);
        String currentItemId = containerId;

        while (currentItemId != null) {
            if (visited.contains(currentItemId)) {
                return true;
            }

            visited.add(currentItemId);

            if (!isEntityExists(ctx, Item.class.getSimpleName(), currentItemId)) {
                String errorMessage = String.format("Item %s does not exist.", currentItemId);
                System.out.println(errorMessage);
                throw new ChaincodeException(errorMessage, ContractErrors.ITEM_NOT_FOUND);
            }

            CompositeKey compositeKey = ctx.getStub().createCompositeKey(Item.class.getSimpleName(), itemId);
            String dbKey = compositeKey.toString();
            byte [] result = ctx.getStub().getState(dbKey);
            Item item = genson.deserialize(result, Item.class);

            currentItemId = item.getContainerId();
        }

        return false;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String sendProductLicense(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);

        String requestId = ctx.getStub().getTxId();
        String senderId = jsonObject.getString("senderId");
        String recipientId = jsonObject.getString("recipientId");
        String dateCreated = jsonObject.getString("dateCreated");
        String dateModified = dateCreated;
        String requestType = RequestType.PRODUCT_LICENSE;
        String requestStatus = RequestStatus.PENDING;
        String productId = jsonObject.getString("productId");
        String details = jsonObject.getString("details");
        
        if (!isEntityExists(ctx, Product.class.getSimpleName(), productId)) {
            String errorMessage = String.format("Product %s does not exist.", requestId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.PRODUCT_NOT_FOUND);
        } else {
            CompositeKey compositeKey = ctx.getStub().createCompositeKey(Product.class.getSimpleName(), productId);
            String dbKey = compositeKey.toString();
            byte [] result = ctx.getStub().getState(dbKey);
            Product product = genson.deserialize(result, Product.class);

            product.setLicenseID(requestId);

            String productStr = genson.serialize(product);
            ctx.getStub().putStringState(dbKey, productStr);
        }

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(ProductLicense.class.getSimpleName(), requestId);
        String dbKey = compositeKey.toString();

        ProductLicense productLicense = ProductLicense
                .builder()
                .requestId(requestId)
                .senderId(senderId)
                .recipientId(recipientId)
                .dateCreated(dateCreated)
                .dateModified(dateModified)
                .requestType(requestType)
                .requestStatus(requestStatus)
                .productId(productId)
                .details(details)
                .build();

        String productLicenseStr = genson.serialize(productLicense);
        ctx.getStub().putStringState(dbKey, productLicenseStr);
        System.out.println("sendProductLicense: " + productLicenseStr);
        return productLicenseStr;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getProductLicense(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String requestId = jsonObject.getString("requestId");

        if (!isEntityExists(ctx, ProductLicense.class.getSimpleName(), requestId)) {
            String errorMessage = String.format("Product license %s does not exist.", requestId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.REQUEST_NOT_FOUND);
        }

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(ProductLicense.class.getSimpleName(), requestId);
        String dbKey = compositeKey.toString();
        byte [] result = ctx.getStub().getState(dbKey);
        ProductLicense productLicense = genson.deserialize(result, ProductLicense.class);

        String productLicenseStr = genson.serialize(productLicense);
        System.out.println("getProductLicense: " + productLicenseStr);
        return productLicenseStr;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String setProductLicenseStatus(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr             );
        String requestId = jsonObject.getString("requestId");
        String requestStatus = jsonObject.getString("requestStatus");
        String dateModified = jsonObject.getString("dateModified");

        if (!isEntityExists(ctx, ProductLicense.class.getSimpleName(), requestId)) {
            String errorMessage = String.format("Product license %s does not exist.", requestId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.REQUEST_NOT_FOUND);
        }

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(ProductLicense.class.getSimpleName(), requestId);
        String dbKey = compositeKey.toString();
        byte [] result = ctx.getStub().getState(dbKey);
        ProductLicense productLicense = genson.deserialize(result, ProductLicense.class);

        productLicense.setRequestStatus(requestStatus);
        productLicense.setDateModified(dateModified);

        String productLicenseStr = genson.serialize(productLicense);
        ctx.getStub().putStringState(dbKey, productLicenseStr);
        System.out.println("setProductLicenseStatus: " + productLicenseStr);
        return productLicenseStr;

    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String addProduct(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);

        String productId = ctx.getStub().getTxId();
        String productName = jsonObject.getString("productName");
        String licenseID = null;
        String creatorId = jsonObject.getString("creatorId");
        String dateCreated = jsonObject.getString("dateCreated");
        String details = jsonObject.getString("details");

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(Product.class.getSimpleName(), productId);
        String dbKey = compositeKey.toString();

        Product product = Product
                .builder()
                .productId(productId)
                .productName(productName)
                .licenseID(licenseID)
                .creatorId(creatorId)
                .dateCreated(dateCreated)
                .details(details)
                .build();


        String productStr = genson.serialize(product);
        ctx.getStub().putStringState(dbKey, productStr);
        System.out.println("addProduct: " + productStr);
        return productStr;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getProduct(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String productId = jsonObject.getString("productId");

        if (!isEntityExists(ctx, Product.class.getSimpleName(), productId)) {
            String errorMessage = String.format("Product %s does not exist.", productId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.PRODUCT_NOT_FOUND);
        }

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(Product.class.getSimpleName(), productId);
        String dbKey = compositeKey.toString();
        byte [] result = ctx.getStub().getState(dbKey);
        Product product = genson.deserialize(result, Product.class);

        String productStr = genson.serialize(product);
        System.out.println("getProduct: " + productStr);
        return productStr;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String addItem(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String itemId = ctx.getStub().getTxId();
        String productId = jsonObject.getString("productId");
        String itemType = jsonObject.getString("itemType");
        String containerId = jsonObject.optString("containerId", null);
        String productionDate = jsonObject.getString("productionDate");
        String expirationDate = jsonObject.getString("expirationDate");
        String creatorId = jsonObject.getString("creatorId");
        String ownerId = jsonObject.getString("ownerId");
        String itemStatus = jsonObject.getString("itemStatus");
        String details = jsonObject.getString("details");

        if (!isEntityExists(ctx, Product.class.getSimpleName(), productId)) {
            String errorMessage = String.format("Product %s does not exist.", productId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.PRODUCT_NOT_FOUND);
        }

        if (!isProductLicenseAccepted(ctx, productId)) {
            String errorMessage = String.format("Product license %s is not accepted.", productId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.PRODUCT_LICENSE_IS_NOT_ACCEPTED);
        }

        if (containerId != null) {
            if (!isEntityExists(ctx, Item.class.getSimpleName(), containerId)) {
                String errorMessage = String.format("Container %s does not exist.", containerId);
                System.out.println(errorMessage);
                throw new ChaincodeException(errorMessage, ContractErrors.ITEM_NOT_FOUND);
            } else {
                CompositeKey compositeKey = ctx.getStub().createCompositeKey(Item.class.getSimpleName(), containerId);
                String dbKey = compositeKey.toString();
                byte [] result = ctx.getStub().getState(dbKey);
                Item container = genson.deserialize(result, Item.class);

                if (!Objects.equals(container.getItemType(), ItemType.CONTAINER)) {
                    String errorMessage = String.format("Item %s is not container.", containerId);
                    System.out.println(errorMessage);
                    throw new ChaincodeException(errorMessage, ContractErrors.ITEM_IS_NOT_CONTAINER);
                }
            }

            if (hasCycleOnContainerAddition(ctx, itemId, containerId)) {
                String errorMessage = String.format("An error occurred while setting the containerId %s for the item.", containerId);
                System.out.println(errorMessage);
                throw new ChaincodeException(errorMessage, ContractErrors.ERROR_SETTING_CONTAINER_ID);
            }
        }

        CompositeKey itemCompositeKey = ctx.getStub().createCompositeKey(Item.class.getSimpleName(), itemId);
        String itemDBKey = itemCompositeKey.toString();

        Item item = Item
                .builder()
                .itemId(itemId)
                .productId(productId)
                .itemType(itemType)
                .containerId(containerId)
                .productionDate(productionDate)
                .expirationDate(expirationDate)
                .creatorId(creatorId)
                .ownerId(ownerId)
                .itemStatus(itemStatus)
                .details(details)
                .build();

        String itemStr = genson.serialize(item);
        ctx.getStub().putStringState(itemDBKey, itemStr);

        System.out.println("addItem: " + itemStr);
        return itemStr;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getItem(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String itemId = jsonObject.getString("itemId");

        if (!isEntityExists(ctx, Item.class.getSimpleName(), itemId)) {
            String errorMessage = String.format("Item %s does not exist.", itemId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.ITEM_NOT_FOUND);
        }

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(Item.class.getSimpleName(), itemId);
        String dbKey = compositeKey.toString();
        byte [] result = ctx.getStub().getState(dbKey);
        Item item = genson.deserialize(result, Item.class);

        String itemStr = genson.serialize(item);
        System.out.println("getItem: " + itemStr);
        return itemStr;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String setContainerIdForItem(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String itemId = jsonObject.getString("itemId");
        String containerId = jsonObject.getString("containerId");

        if (!isEntityExists(ctx, Item.class.getSimpleName(), itemId)) {
            String errorMessage = String.format("Item %s does not exist.", itemId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.ITEM_NOT_FOUND);
        }

        if (!isEntityExists(ctx, Item.class.getSimpleName(), containerId)) {
            String errorMessage = String.format("Container %s does not exist.", containerId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.ITEM_NOT_FOUND);
        } else {
            CompositeKey compositeKey = ctx.getStub().createCompositeKey(Item.class.getSimpleName(), containerId);
            String dbKey = compositeKey.toString();
            byte [] result = ctx.getStub().getState(dbKey);
            Item container = genson.deserialize(result, Item.class);

            if (!Objects.equals(container.getItemType(), ItemType.CONTAINER)) {
                String errorMessage = String.format("Item %s is not container.", containerId);
                System.out.println(errorMessage);
                throw new ChaincodeException(errorMessage, ContractErrors.ITEM_IS_NOT_CONTAINER);
            }
        }

        if (hasCycleOnContainerAddition(ctx, itemId, containerId)) {
            String errorMessage = String.format("An error occurred while setting the containerId %s for the item.", containerId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.ERROR_SETTING_CONTAINER_ID);
        }

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(Item.class.getSimpleName(), itemId);
        String dbKey = compositeKey.toString();
        byte [] result = ctx.getStub().getState(dbKey);
        Item item = genson.deserialize(result, Item.class);

        item.setContainerId(containerId);

        String itemStr = genson.serialize(item);
        ctx.getStub().putStringState(dbKey, itemStr);
        System.out.println("setContainerIdForItem: " + itemStr);
        return itemStr;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public String removeContainerIdForItem(Context ctx, String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String itemId = jsonObject.getString("itemId");

        if (!isEntityExists(ctx, Item.class.getSimpleName(), itemId)) {
            String errorMessage = String.format("Item %s does not exist.", itemId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ContractErrors.ITEM_NOT_FOUND);
        }

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(Item.class.getSimpleName(), itemId);
        String dbKey = compositeKey.toString();
        byte [] result = ctx.getStub().getState(dbKey);
        Item item = genson.deserialize(result, Item.class);

        item.setContainerId(null);

        String itemStr = genson.serialize(item);
        ctx.getStub().putStringState(dbKey, itemStr);
        System.out.println("removeContainerIdForItem: " + itemStr);
        return itemStr;
    }


}