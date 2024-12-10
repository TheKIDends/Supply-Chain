package com.supplychain.userservice.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.supplychain.userservice.chaincode.Config;
import com.supplychain.userservice.chaincode.client.RegisterUserHyperledger;
import com.supplychain.userservice.data.*;
import com.supplychain.userservice.enumeration.UserRole;
import com.supplychain.userservice.model.*;
import com.supplychain.userservice.repository.*;
import com.supplychain.userservice.util.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarrierRepository carrierRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<Map<String, Object>> getAllUsers(String accessToken) {
        Map<String, Object> response = new HashMap<>();
        try {
            String userName = null;
            try {
                userName = TokenUtils.getUsernameFromToken(accessToken);
            } catch (JWTVerificationException e) {
                response.put("message", "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            User user = userRepository.findByUsername(userName);

            if (!Objects.equals(user.getRole(), UserRole.ADMIN)) {
                response.put("message", "Không có quyền truy cập");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            List<User> users = userRepository.findAll();

            response.put("message", "Lấy dữ liệu thành công");
            response.put("data", users);
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("message", "Lỗi khi truy xuất dữ liệu người dùng.");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getUserByToken(String accessToken) {
        Map<String, Object> response = new HashMap<>();
        try {

            String userName = TokenUtils.getUsernameFromToken(accessToken);

            User user = userRepository.findByUsername(userName);

            response.put("message", "Lấy dữ liệu thành công");
            response.put("data", user);
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("message", "Lỗi khi truy xuất dữ liệu người dùng.");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getUserById(String accessToken, String userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userRepository.findUserById(userId);
            if (Objects.equals(user.getRole(), UserRole.ADMIN)) {
                user =  adminRepository.findAdminById(userId);
            } else
            if (Objects.equals(user.getRole(), UserRole.BUSINESS)) {
                user =  businessRepository.findBusinessById(userId);
            } else
            if (Objects.equals(user.getRole(), UserRole.CARRIER)) {
                user =  carrierRepository.findCarrierById(userId);
            } else
            if (Objects.equals(user.getRole(), UserRole.CUSTOMER)) {
                user =  customerRepository.findCustomerById(userId);
            }

            if (user == null) {
                response.put("message", "Người dùng không tồn tại");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            String userName = null;
            try {
                userName = TokenUtils.getUsernameFromToken(accessToken);
            } catch (JWTVerificationException e) {
                response.put("message", "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            if (!Objects.equals(user.getRole(), UserRole.ADMIN) && !Objects.equals(userName, user.getUsername())) {
                response.put("message", "Không có quyền truy cập");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            response.put("message", "Lấy dữ liệu thành công");
            response.put("data", user);
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("message", "Lỗi khi truy xuất dữ liệu người dùng.");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> editUser(String accessToken, UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            String userName = null;

            try {
                userName = TokenUtils.getUsernameFromToken(accessToken);
            } catch (JWTVerificationException e) {
                response.put("message", "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            User user = userRepository.findUserById(userDTO.getId());

            if (user == null) {
                response.put("message", "Người dùng không tồn tại");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (!Objects.equals(user.getRole(), UserRole.ADMIN) && !Objects.equals(userName, user.getUsername())) {
                response.put("message", "Không có quyền truy cập");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            BeanUtils.copyProperties(userDTO, user, "id", "username", "phoneNumber", "role", "designation");
            userRepository.save(user);

            response.put("message", "Cập nhật dữ liệu thành công");
            response.put("data", user);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra! Vui lòng thử lại");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> saveUser(UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (Objects.equals(userDTO.getRole(), UserRole.ADMIN)) {

            AdminDTO adminDTO = AdminDTO.convertUserDTOToAdminDTO(userDTO);
            Admin admin = AdminDTO.dtoToEntity(adminDTO);

            Admin savedAdmin = adminRepository.save(admin);

            if (savedAdmin != null && savedAdmin.getId() != null) {

                try {
                    RegisterUserHyperledger.enrollOrgAppUsers(admin.getPhoneNumber(), Config.ORG1, admin.getId());

                } catch (Exception exception) {

                    adminRepository.delete(admin);
                    System.out.println("Error in registering user in Hyperledger: " + exception.getMessage());

                    response.put("message", "Không thể đăng ký người dùng vào Hyperledger");
                    response.put("data", null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                response.put("message", "Đăng ký thành công");
                response.put("data", AdminDTO.entityToDTO(savedAdmin));
                return ResponseEntity.ok(response);
            } else {

                response.put("message", "Không thể đăng ký người dùng vào cơ sở dữ liệu");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        }

        if (Objects.equals(userDTO.getRole(), UserRole.BUSINESS)) {

            BusinessDTO businessDTO = BusinessDTO.convertUserDTOToBusinessDTO(userDTO);
            Business business = BusinessDTO.dtoToEntity(businessDTO);

            Business savedBusiness = businessRepository.save(business);

            if (savedBusiness != null && savedBusiness.getId() != null) {

                try {
                    RegisterUserHyperledger.enrollOrgAppUsers(business.getPhoneNumber(), Config.ORG2, business.getId());

                } catch (Exception exception) {

                    businessRepository.delete(business);
                    System.out.println("Error in registering user in Hyperledger: " + exception.getMessage());

                    response.put("message", "Không thể đăng ký người dùng vào Hyperledger");
                    response.put("data", null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                response.put("message", "Đăng ký thành công");
                response.put("data", BusinessDTO.entityToDTO(savedBusiness));
                return ResponseEntity.ok(response);
            } else {

                response.put("message", "Không thể đăng ký người dùng vào cơ sở dữ liệu");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        }

        if (Objects.equals(userDTO.getRole(), UserRole.CARRIER)) {

            CarrierDTO carrierDTO = CarrierDTO.convertUserDTOToCarrierDTO(userDTO);
            Carrier carrier = CarrierDTO.dtoToEntity(carrierDTO);

            Carrier savedCarrier = carrierRepository.save(carrier);

            if (savedCarrier != null && savedCarrier.getId() != null) {

                try {
                    RegisterUserHyperledger.enrollOrgAppUsers(carrier.getPhoneNumber(), Config.ORG3, carrier.getId());

                } catch (Exception exception) {

                    carrierRepository.delete(carrier);
                    System.out.println("Error in registering user in Hyperledger: " + exception.getMessage());

                    response.put("message", "Không thể đăng ký người dùng vào Hyperledger");
                    response.put("data", null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                response.put("message", "Đăng ký thành công");
                response.put("data", CarrierDTO.entityToDTO(savedCarrier));
                return ResponseEntity.ok(response);
            } else {

                response.put("message", "Không thể đăng ký người dùng vào cơ sở dữ liệu");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        }

        if (Objects.equals(userDTO.getRole(), UserRole.CUSTOMER)) {

            CustomerDTO customerDTO = CustomerDTO.convertUserDTOToCustomerDTO(userDTO);
            Customer customer = CustomerDTO.dtoToEntity(customerDTO);

            Customer savedCustomer = customerRepository.save(customer);

            if (savedCustomer != null && savedCustomer.getId() != null) {

                try {
                    RegisterUserHyperledger.enrollOrgAppUsers(customer.getPhoneNumber(), Config.ORG4, customer.getId());

                } catch (Exception exception) {

                    customerRepository.delete(customer);
                    System.out.println("Error in registering user in Hyperledger: " + exception.getMessage());

                    response.put("message", "Không thể đăng ký người dùng vào Hyperledger");
                    response.put("data", null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                response.put("message", "Đăng ký thành công");
                response.put("data", CustomerDTO.entityToDTO(savedCustomer));
                return ResponseEntity.ok(response);
            } else {

                response.put("message", "Không thể đăng ký người dùng vào cơ sở dữ liệu");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        }

        response.put("message", "Chức danh không hợp lệ");
        response.put("data", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public ResponseEntity<Map<String, Object>> login(String phoneNumber, String password) {
        Map<String, Object> response = new HashMap<>();

        try {
            User user = userRepository.findByPhoneNumber(phoneNumber);

            // Kiểm tra nếu người dùng không tồn tại
            if (user == null) {
                response.put("message", "Người dùng không tồn tại");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            UserDTO dto = new UserDTO();

            BeanUtils.copyProperties(user, dto);
            if (passwordEncoder.matches(password, dto.getPassword())) {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 10000)))
//                        .withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 10000)))
                        .sign(algorithm);

                String refreshToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 10000)))
                        .sign(algorithm);
                dto.setAccessToken(accessToken);
                dto.setRefreshToken(refreshToken);

                response.put("message", "Đăng nhập thành công");
                response.put("data", dto);
                return ResponseEntity.ok(response);
            } else {
                // Mật khẩu không khớp
                response.put("message", "Sai mật khẩu");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra! Vui lòng thử lại");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
}