package com.dietrich.psiu.util;

import com.dietrich.psiu.handler.MyUserDetails;
import com.dietrich.psiu.model.user.Person;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtils {
    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    private static String ID = "psiuApi";
    private static String SECRET = "test";

    private static final Integer EXPIRATION_TIME = 999999999;

    public static String getJwtToken(Person user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return createToken(user, authorities);
    }

    private static String createToken(Person user, List<GrantedAuthority> authorities) {
        String token = Jwts.builder()
                .setId(ID)
                .setSubject(user.getEmail())
                .claim("authorities", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .claim("user", user)
                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET.getBytes()).compact();
        return PREFIX + token;
    }
}
