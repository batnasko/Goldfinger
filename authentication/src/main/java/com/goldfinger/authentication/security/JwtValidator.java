package com.goldfinger.authentication.security;

import com.goldfinger.authentication.models.JwtUserDetails;
import com.goldfinger.authentication.models.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

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
            jwtUserDetails.setUsername(body.getSubject());
            jwtUserDetails.setId(Long.parseLong((String) body.get("id")));
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
