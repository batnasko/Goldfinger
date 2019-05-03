package com.goldfinger.authentication.services.contracts;

import com.goldfinger.authentication.models.JwtUserDetails;

public interface UserService {
    boolean addUser(JwtUserDetails jwtUserDetails);
    String getToken(String username, String password);
}
