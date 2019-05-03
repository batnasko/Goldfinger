package com.goldfinger.authentication.security;


import com.goldfinger.authentication.models.JwtUser;
import com.goldfinger.authentication.models.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JwtGenerator {
    private static final String SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    public String generate(JwtUserDetails jwtUserDetails) {

        Claims claims = Jwts.claims().setSubject(String.valueOf(jwtUserDetails.getId()));
        claims.put("username", String.valueOf(jwtUserDetails.getUsername()));
        claims.put("roles", parsRoles(jwtUserDetails.getAuthorities()));
        claims.put("firstName", jwtUserDetails.getFirstName());
        claims.put("lastName", jwtUserDetails.getLastName());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private String parsRoles(Collection<? extends GrantedAuthority> roles){
        StringBuilder sb = new StringBuilder();
        for (GrantedAuthority ga : roles) {
            sb.append(ga.getAuthority()).append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }
}
