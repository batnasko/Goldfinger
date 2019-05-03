package com.goldfinger.authentication.controllers;

import com.goldfinger.authentication.models.JwtUserDetails;
import com.goldfinger.authentication.models.Role;
import com.goldfinger.authentication.security.JwtGenerator;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
public class Contorller {

    @GetMapping
    public String getToken(){
        JwtGenerator generator = new JwtGenerator();
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("vvv"));
        roles.add(new Role("ttttt"));
        return generator.generate(new JwtUserDetails(1, "sadasd", "nasko", "basko", "dasdasdasda", roles));
    }

    @GetMapping("/user")
    @Secured({"admin"})
    public String hello(){
        return "Hello";
    }
}
