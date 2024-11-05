package com.blockchain.supplychain.util;

import com.blockchain.supplychain.chaincode.Config;
import com.blockchain.supplychain.chaincode.client.RegisterUserHyperledger;
import com.blockchain.supplychain.entity.ProductItem;
import com.blockchain.supplychain.entity.Product;
import com.blockchain.supplychain.entity.request.ProductLicense;
import com.blockchain.supplychain.enumeration.ProductItemType;
import com.blockchain.supplychain.enumeration.RequestStatus;
import com.blockchain.supplychain.model.Business;
import com.blockchain.supplychain.repository.BusinessRepository;
import com.blockchain.supplychain.repository.UserRepository;
import com.blockchain.supplychain.service.HyperledgerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class TestInit implements CommandLineRunner {
    private final static Logger LOG = Logger.getLogger(TestInit.class.getName());

    private Business business1 = null;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private HyperledgerService hyperledgerService;

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    private void init() throws Exception {
        try {
            business1 = Business
                    .builder()
                    .name("Công ty TNHH Việt Hùng")
                    .address("144 Xuân Thủy, Cầu Giấy, Hà Nội")
                    .phoneNumber("0912345678")
                    .businessLicenseNumber("9876543210")
                    .directorName("Nguyễn Châu Khanh")
                    .directorIDNumber("0123456789")
                    .username("viethung@gmail.com")
                    .email("viethung@gmail.com")
                    .password("viethung@gmail.com")
                    .enabled(true)
                    .role("Business")
                    .build();
            businessRepository.save(business1);

            RegisterUserHyperledger.enrollOrgAppUsers(business1.getEmail(), Config.ORG1, business1.getId());

        }
        catch (Exception exception) {
            businessRepository.delete(business1);
            System.out.println(exception);
        }

//        addProduct();
//        getProduct();
//        sendProductLicense();
//        getProductLicense();
        setProductLicenseStatus();

//        addItem();
//        addItem();
//        addItem();

//        setContainerIdForItem();

//        removeContainerIdForItem();


        getItem();
        LOG.info("TEST INIT IS FINISHED");
    }

    private void addItem() {
        String userId = business1.getId();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", "3c4c0149937c00ade0d2a4a86e1cf079555092c0a0dfb077aab43c9af6adccd1");
        jsonObject.put("itemType", ProductItemType.CONTAINER);
        jsonObject.put("containerId", JSONObject.NULL);
        jsonObject.put("productionDate", TimeUtils.getCurrentTimeStrInVietNam());
        jsonObject.put("expirationDate", "30/7/2025");
        jsonObject.put("creatorId", userId);
        jsonObject.put("ownerId", userId);
        jsonObject.put("itemStatus", "abc");
        jsonObject.put("details", "Dưa hấu không hạt");

        try {
            ProductItem productItem = hyperledgerService.addItem(
                    business1,
                    jsonObject
            );
            System.out.println("productItem: " + productItem);

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void addProduct() {
        String userId = business1.getId();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productName", "Dưa hấu");
        jsonObject.put("creatorId", userId);
        jsonObject.put("dateCreated", TimeUtils.getCurrentTimeStrInVietNam());
        jsonObject.put("details", "Dưa hấu không hạt");

        try {
            Product product = hyperledgerService.addProduct(
                    business1,
                    jsonObject
            );
            System.out.println("product: " + product);

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void getProduct() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", "3c4c0149937c00ade0d2a4a86e1cf079555092c0a0dfb077aab43c9af6adccd1");

        try {
            Product product = hyperledgerService.getProduct(
                    business1,
                    jsonObject
            );

            System.out.println("getProduct: " + product);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void sendProductLicense() {
        String userId = business1.getId();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("senderId", userId);
        jsonObject.put("recipientId", "abcfff");
        jsonObject.put("dateCreated", TimeUtils.getCurrentTimeStrInVietNam());
        jsonObject.put("productId", "711628b8f8288e9c1adc230e8d3ef37246e2388fe36878b5b6a748c3dd6d5d20");
        jsonObject.put("details", "Chi tiết");

        try {
            ProductLicense productLicense = hyperledgerService.sendProductLicense(
                    business1,
                    jsonObject
            );
            System.out.println("productLicense: " + productLicense);

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void getProductLicense() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestId", "67bd9f0e2a2b48712e7e9a2b6cbb925be00fbad0a7d6f7464510cee866f59c5d");

        try {
            ProductLicense productLicense = hyperledgerService.getProductLicense(
                    business1,
                    jsonObject
            );

            System.out.println("productLicense: " + productLicense);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void getItem() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemId", "52ebe1adf46ee44e36c49f5df34aba875dc59a1054113e397a37b023b305d205");

        try {
            ProductItem productItem = hyperledgerService.getItem(
                    business1,
                    jsonObject
            );

            System.out.println("getItem: " + productItem);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void setProductLicenseStatus() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestId", "dd62b177e82c30ac9d63bd478210f6190439cdd804e4d91969b3534c3a700f90");
        jsonObject.put("requestStatus", RequestStatus.ACCEPTED);
        jsonObject.put("dateModified", TimeUtils.getCurrentTimeStrInVietNam());

        try {
            ProductLicense productLicense = hyperledgerService.setProductLicenseStatus(
                    business1,
                    jsonObject
            );

            System.out.println("setProductLicenseStatus: " + productLicense);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void setContainerIdForItem() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemId", "52ebe1adf46ee44e36c49f5df34aba875dc59a1054113e397a37b023b305d205");
        jsonObject.put("containerId", "d27b6bc058244874556530497afbbf02850f6085f011c491e094064f760bf2a6");

        try {
            ProductItem productItem = hyperledgerService.setContainerIdForItem(
                    business1,
                    jsonObject
            );

            System.out.println("setContainerIdForItem: " + productItem);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void removeContainerIdForItem() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemId", "52ebe1adf46ee44e36c49f5df34aba875dc59a1054113e397a37b023b305d205");

        try {
            ProductItem productItem = hyperledgerService.removeContainerIdForItem(
                    business1,
                    jsonObject
            );

            System.out.println("removeContainerIdForItem: " + productItem);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

}