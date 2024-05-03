package com.blockchain.supplychain.controllers;

import com.blockchain.supplychain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import com.blockchain.supplychain.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.base-path}")
public class AuthController {
    private final UserRepository userRepository;

    @Value("${param.email}")
    private String PARAM_EMAIL;

    @Value("${param.phoneNumber}")
    private String PARAM_PHONE_NUMBER;

    @Value("${param.password}")
    private String PARAM_PASSWORD;

    @Value("${param.fullName}")
    private String PARAM_FULL_NAME;

    @Value("${param.hashedPassword}")
    private String PARAM_HASHED_PASSWORD;

    @Value("${param.usersImageDefault}")
    private String PARAM_USERS_IMAGE_DEFAULT;

//    private final String REGISTER_EMAIL_EXISTS;
//
//    private final String REGISTER_PHONE_EXISTS;
//
//    private final String REGISTER_SUCCESS;
//
//    private final String REGISTER_FAILED;

    @Autowired
    public AuthController(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
//        this.REGISTER_EMAIL_EXISTS = messageSource.getMessage("response.register.email.exists", null, LocaleContextHolder.getLocale());
//        this.REGISTER_PHONE_EXISTS = messageSource.getMessage("response.register.phone.exists", null, LocaleContextHolder.getLocale());
//        this.REGISTER_SUCCESS = messageSource.getMessage("response.register.success", null, LocaleContextHolder.getLocale());
//        this.REGISTER_FAILED = messageSource.getMessage("response.register.failed", null, LocaleContextHolder.getLocale());
    }

    @PostMapping("${endpoint.public.login}")
    public ResponseEntity<?> login(HttpServletRequest request) {
        String email = request.getParameter(PARAM_EMAIL);
        String phoneNumber = request.getParameter(PARAM_PHONE_NUMBER);

        String plainPassword = request.getParameter(PARAM_PASSWORD);

//        User findByEmail = userRepository.findUsersByEmail(email);
//        User findByPhoneNumber = userRepository.findUsersByEmail(phoneNumber);

//        if (findByEmail == null && findByPhoneNumber == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_FAILED);
//            return ResponseEntity.ok("fail");
//        }

//        User user = (findByEmail != null) ? findByEmail : findByPhoneNumber;

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        if (!passwordEncoder.matches(plainPassword, user.getHashedPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LOGIN_FAILED);
//        }

//        String accessToken = jwtTokenUtil.generateAccessToken(email);

//        Map<String, String> tokens = new HashMap<>();
//        tokens.put(TOKEN_ACCESS, accessToken);

//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.toString(), LOGIN_SUCCESS, tokens));
        return ResponseEntity.ok("ok");
    }

    @PostMapping("${endpoint.public.register}")
    public ResponseEntity<String> registerUser(HttpServletRequest request) {
        try {
            String fullName = request.getParameter(PARAM_FULL_NAME);
            String email = request.getParameter(PARAM_EMAIL);
            String phoneNumber = request.getParameter(PARAM_PHONE_NUMBER);
            String plainPassword = request.getParameter(PARAM_HASHED_PASSWORD);

//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String hashedPassword = passwordEncoder.encode(plainPassword);

            System.out.println(fullName);
//            User findByEmail = userRepository.findUsersByEmail(email);
//            List<User> findByPhoneNumber = userRepository.findUsersByPhoneNumber(phoneNumber);
//
//            if (findByEmail != null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(REGISTER_EMAIL_EXISTS);
//            }
//
//            if (!findByPhoneNumber.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(REGISTER_PHONE_EXISTS);
//            }
//
            User users = new User(fullName, email, plainPassword, phoneNumber, false, PARAM_USERS_IMAGE_DEFAULT);
            userRepository.save(users);
            return ResponseEntity.ok("ok");
        } catch (Exception exception) {
            return ResponseEntity.ok("fail");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(REGISTER_FAILED);
        }
    }
}
