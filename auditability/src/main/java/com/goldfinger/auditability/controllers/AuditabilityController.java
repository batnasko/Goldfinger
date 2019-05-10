package com.goldfinger.auditability.controllers;

import com.goldfinger.auditability.models.*;
import com.goldfinger.auditability.services.contracts.AuditabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/auditability")
public class AuditabilityController {
    private static final String LOG_ADDED = "Log added";
    private static final String INVALID_SEARCH = "Invalid search";
    private AuditabilityService auditabilityService;

    @Autowired
    public AuditabilityController(AuditabilityService auditabilityService) {
        this.auditabilityService = auditabilityService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED, reason = LOG_ADDED)
    public void addNewLog(@RequestBody HashMap<String, String> log) {
        try {
            auditabilityService.addLog(log);
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/get")
    public List<Map<String, String>> getLogs(@RequestBody Search search) {
        try {
            return auditabilityService.getLogs(search.getSearch());
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_SEARCH);
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/csv")
    public String exportLogsToCvs(@RequestBody Export export) {
        try {
            return auditabilityService.exportLogsToCSV(export);
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_SEARCH);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
