package com.supplychain.userservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

public class TokenUtils {
    public static String getUserIdFromToken(String accessToken) throws JWTVerificationException {
        // Xác thực token bằng HMAC256
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        // Giải mã và xác thực token
        DecodedJWT decodedJWT = verifier.verify(accessToken);

        // Lấy thông tin 'userId' từ payload của token
        String userId = decodedJWT.getClaim("userId").asString();
        return userId;
    }
}
