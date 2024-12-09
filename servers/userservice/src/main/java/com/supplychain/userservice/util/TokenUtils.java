package com.supplychain.userservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

public class TokenUtils {
    public static String getUsernameFromToken(String accessToken) throws JWTVerificationException {
        // Xác thực token bằng HMAC256
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decodedJWT = verifier.verify(accessToken);

        return decodedJWT.getSubject();
    }
}
