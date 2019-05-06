package com.goldfinger.authentication.security;

import com.goldfinger.authentication.models.*;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtValidator {

    private static final String SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    public JwtUserDetails validate(String token) {

        JwtUserDetails jwtUserDetails = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUserDetails = new JwtUserDetails();
            jwtUserDetails.setId(Long.parseLong(body.getSubject()));
            jwtUserDetails.setUsername(String.valueOf(body.get("username")));
            jwtUserDetails.setFirstName(String.valueOf(body.get("firstName")));
            jwtUserDetails.setLastName(String.valueOf(body.get("lastName")));
            jwtUserDetails.setAuthorities(parseRoles(String.valueOf(body.get("roles"))));
        }catch (Exception e){
            System.out.println(e);
        }

        return jwtUserDetails;
    }

    private Collection<Role> parseRoles(String commaSeparatedStringRoles){
        Collection<Role> roles = new ArrayList<>();
        for (String role: commaSeparatedStringRoles.split(",")) {
            roles.add(new Role(role));
        }

        return roles;
    }
}
