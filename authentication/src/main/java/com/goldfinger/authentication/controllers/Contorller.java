package com.goldfinger.authentication.controllers;

import com.goldfinger.authentication.models.JwtUserDetails;
import com.goldfinger.authentication.security.JwtGenerator;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Arrays;

@RestController
@RequestMapping
public class Contorller {

    @GetMapping
    public String getToken(){
        JwtGenerator generator = new JwtGenerator();

        return generator.generate(new JwtUserDetails(1, "sadasd", "nasko", "basko", "dasdasdasda", AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
    }

    @GetMapping("/user")
    @Secured({"admin"})
    public String hello(){
        return "Hello";
    }
}
