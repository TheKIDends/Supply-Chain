package com.supplychain.userservice.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.supplychain.userservice.data.User;
import com.supplychain.userservice.enumeration.Designation;
import com.supplychain.userservice.enumeration.UserRole;
import com.supplychain.userservice.model.BusinessDTO;
import com.supplychain.userservice.model.UserDTO;
import com.supplychain.userservice.repository.BusinessRepository;
import com.supplychain.userservice.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public UserDTO saveUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

//        if (Objects.equals(userDTO.getRole(), UserRole.BUSINESS)) {
            BusinessDTO businessDTO = BusinessDTO.convertUserDTOToBusinessDTO(userDTO);
            return BusinessDTO.entityToDTO(businessRepository.save(BusinessDTO.dtoToEntity(businessDTO)));
//        }

//        return UserDTO.entityToDTO(userRepository.save(UserDTO.dtoToEntity(userDTO))).toString();
    }

    public UserDTO login(String phoneNumber, String password) {
        User user = null;
        user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            user = businessRepository.findByPhoneNumber(phoneNumber);
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
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 10000)))
                        .sign(algorithm);

                String refreshToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 10000)))
                        .sign(algorithm);
                dto.setAccessToken(accessToken);
                dto.setRefreshToken(refreshToken);
            }
        }
        return dto;
    }
}