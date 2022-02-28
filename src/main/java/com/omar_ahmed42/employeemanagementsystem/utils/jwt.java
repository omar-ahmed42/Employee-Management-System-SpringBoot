package com.omar_ahmed42.employeemanagementsystem.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static java.util.Arrays.stream;

public class jwt {

    public static Map<String, Object> decodeHMAC256(String token){
        Algorithm algorithm = Algorithm.HMAC256("insert your secret here"
                .getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        Map<String, Object> decodedTokenMap = new HashMap<>();
        decodedTokenMap.put("username", username);
        decodedTokenMap.put("authorities", authorities);
        return decodedTokenMap;
    }
}
