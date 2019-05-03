package com.goldfinger.authentication.controllers;

import com.goldfinger.authentication.models.JwtUserDetails;
import com.goldfinger.authentication.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {

    private static final String USER_CREATED = "User created!";

    private UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED, reason = USER_CREATED)
    public boolean addUser(@RequestBody JwtUserDetails jwtUserDetails){
        try {
            return service.addUser(jwtUserDetails);
        }catch (EntityExistsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/token")
    public String getToken(@RequestBody Map<String, String> usernamePassword){
        try {
            return service.getToken(usernamePassword.get("username"), usernamePassword.get("password"));
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
