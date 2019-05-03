package com.goldfinger.authentication.services;

import com.goldfinger.authentication.models.JwtUserDetails;
import com.goldfinger.authentication.models.Role;
import com.goldfinger.authentication.repositories.UserRepository;
import com.goldfinger.authentication.security.JwtGenerator;
import com.goldfinger.authentication.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_ALREADY_EXIST = "User already exist!";
    private static final String USER_NOT_FOUND = "User not found!";
    private static long USER_ROLE = 2;

    private UserRepository repository;
    private JwtGenerator jwtGenerator;

    @Autowired
    public UserServiceImpl(UserRepository repository, JwtGenerator jwtGenerator) {
        this.repository = repository;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
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

    @Override
    public String getToken(String username, String password){
        List<JwtUserDetails> users = repository.findByUsernameAndPassword(username, password);
        if (users.isEmpty()){
            throw new EntityNotFoundException(USER_NOT_FOUND);
        }

        return jwtGenerator.generate(users.get(0));
    }
}
