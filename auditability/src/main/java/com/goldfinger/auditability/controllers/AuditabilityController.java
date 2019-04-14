package com.goldfinger.auditability.controllers;

import com.goldfinger.auditability.services.contracts.AuditabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequestMapping("/auditability")
public class AuditabilityController {
    private static final String LOG_ADDED = "Log added";
    private AuditabilityService auditabilityService;

    @Autowired
    public AuditabilityController(AuditabilityService auditabilityService) {
        this.auditabilityService = auditabilityService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED, reason =LOG_ADDED)
    public void addNewLog(@RequestBody HashMap<String, String> log) {
        try {
            auditabilityService.addLog(log);
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
