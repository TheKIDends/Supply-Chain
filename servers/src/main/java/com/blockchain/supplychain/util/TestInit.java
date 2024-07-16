package com.blockchain.supplychain.util;

import com.blockchain.supplychain.chaincode.Config;
import com.blockchain.supplychain.chaincode.client.RegisterUserHyperledger;
import com.blockchain.supplychain.entity.ProductLicense;
import com.blockchain.supplychain.model.Company;
import com.blockchain.supplychain.repository.CompanyRepository;
import com.blockchain.supplychain.repository.UserRepository;
import com.blockchain.supplychain.service.HyperledgerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class TestInit implements CommandLineRunner {
    private final static Logger LOG = Logger.getLogger(TestInit.class.getName());

    private Company company1 = null;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private HyperledgerService hyperledgerService;

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    private void init() throws Exception {
        try {
            company1 = Company
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
                    .role("Company")
                    .build();
            companyRepository.save(company1);

            RegisterUserHyperledger.enrollOrgAppUsers(company1.getEmail(), Config.ORG1, company1.getId());

        }
        catch (Exception exception) {
            companyRepository.delete(company1);
            System.out.println(exception);
        }

        sendProductLicense();
        getProductLicense();

        LOG.info("TEST INIT IS FINISHED");
    }

    private void sendProductLicense() {
        String userId = company1.getId();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("senderId", userId);
        jsonObject.put("recipientId", "abc");
        jsonObject.put("dateCreated", "abc");
        jsonObject.put("dateModified", "abc");
        jsonObject.put("companyId", "abc");
        jsonObject.put("productId", "abc");
        jsonObject.put("details", "abc");

        try {
            ProductLicense productLicense = hyperledgerService.sendProductLicense(
                    company1,
                    jsonObject);
            System.out.println("productLicense: " + productLicense);

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void getProductLicense() {
        try {
            ProductLicense productLicense = hyperledgerService.getProductLicense(
                    company1,
                    "3c9702759fae251355fa04c6a231ad3b38ab85473a44101ca71abbfa6e3bfaed"
            );

            System.out.println("productLicense: " + productLicense);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

}