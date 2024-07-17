package com.blockchain.supplychain.util;

import com.blockchain.supplychain.chaincode.Config;
import com.blockchain.supplychain.chaincode.client.RegisterUserHyperledger;
import com.blockchain.supplychain.entity.Item;
import com.blockchain.supplychain.entity.Product;
import com.blockchain.supplychain.entity.ProductLicense;
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
//
        addItem();
//        getProduct();
//        sendProductLicense();
//        getProductLicense();
//        setProductLicenseStatus();

        LOG.info("TEST INIT IS FINISHED");
    }

    private void addItem() {
        String userId = business1.getId();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", "b4591ec063506241353b55cea6e0ffab6b05d1e829b49a4a2e774221c28d925e");
        jsonObject.put("expirationDate", "30/7/2025");
        jsonObject.put("creatorId", userId);
        jsonObject.put("ownerId", userId);
        jsonObject.put("itemStatus", "abc");
        jsonObject.put("details", "Dưa hấu không hạt");


        try {
            Item item = hyperledgerService.addItem(
                    business1,
                    jsonObject
            );
            System.out.println("item: " + item);

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void addProduct() {
        String userId = business1.getId();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productName", "Dưa hấu");
        jsonObject.put("creatorId", userId);
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
        try {
            Product product = hyperledgerService.getProduct(
                    business1,
                    "4460bd9f23aefca5c96cb011437bbdf6a5e61db94dad1d27a9b3d75947146f0a"
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
        jsonObject.put("productId", "b4591ec063506241353b55cea6e0ffab6b05d1e829b49a4a2e774221c28d925e");
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
        try {
            ProductLicense productLicense = hyperledgerService.getProductLicense(
                    business1,
                    "3c9702759fae251355fa04c6a231ad3b38ab85473a44101ca71abbfa6e3bfaed"
            );

            System.out.println("productLicense: " + productLicense);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void setProductLicenseStatus() {
        try {
            ProductLicense productLicense = hyperledgerService.setProductLicenseStatus(
                    business1,
                    "c501fbbb516e1c0fb0a5f41ff1a4d98e7a868e869867a4951213b15b7023f5ff",
                    RequestStatus.ACCEPTED
            );

            System.out.println("setProductLicenseStatus: " + productLicense);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

}