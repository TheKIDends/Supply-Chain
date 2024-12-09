package com.supplychain.userservice.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.supplychain.userservice.chaincode.Config;
import com.supplychain.userservice.chaincode.client.RegisterUserHyperledger;
import com.supplychain.userservice.data.Business;
import com.supplychain.userservice.data.User;
import com.supplychain.userservice.enumeration.UserRole;
import com.supplychain.userservice.model.BusinessDTO;
import com.supplychain.userservice.model.UserDTO;
import com.supplychain.userservice.repository.BusinessRepository;
import com.supplychain.userservice.repository.UserRepository;
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
    private PasswordEncoder passwordEncoder;


    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
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

            String userId = TokenUtils.getUserIdFromToken(accessToken);

            User user = userRepository.getById(userId);

            response.put("message", "Lấy dữ liệu thành công");
            response.put("data", user);
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            response.put("message", "Lỗi khi truy xuất dữ liệu người dùng.");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> saveUser(UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
     
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

        response.put("message", "Chức danh không hợp lệ");
        response.put("data", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    public ResponseEntity<Map<String, Object>> login(String phoneNumber, String password) {
        Map<String, Object> response = new HashMap<>();

        User user = null;
        user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            user = businessRepository.findByPhoneNumber(phoneNumber);
        }

        // Kiểm tra nếu người dùng không tồn tại
        if (user == null) {
            response.put("message", "Người dùng không tồn tại");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


        UserDTO dto = null;
        if (Objects.equals(user.getRole(), UserRole.BUSINESS)) {
            dto = new BusinessDTO();
        }
        
        if (user != null && dto != null) {
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
        }

        // Nếu không tìm được DTO tương ứng
        response.put("message", "Có lỗi xảy ra! Vui lòng thử lại");
        response.put("data", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}