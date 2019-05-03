package com.goldfinger.authentication.services;

import com.goldfinger.authentication.models.JwtUserDetails;
import com.goldfinger.authentication.models.Role;
import com.goldfinger.authentication.repositories.UserRepository;
import com.goldfinger.authentication.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_ALREADY_EXIST = "User already exist!";
    private static long USER_ROLE = 2;

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    public boolean addUser(JwtUserDetails jwtUserDetails){
        if (repository.findByUsername(jwtUserDetails.getUsername()).isEmpty()){
            Collection<Role> roles = new ArrayList<>();
            roles.add(new Role(USER_ROLE));
            jwtUserDetails.setAuthorities(roles);
            repository.save(jwtUserDetails);
            return true;
        }
        throw new EntityExistsException(USER_ALREADY_EXIST);
    }
}
