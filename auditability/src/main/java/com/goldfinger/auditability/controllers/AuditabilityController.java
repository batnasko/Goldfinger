package com.goldfinger.auditability.controllers;

import com.goldfinger.auditability.models.Export;
import com.goldfinger.auditability.models.SearchFilter;
import com.goldfinger.auditability.services.contracts.AuditabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/get")
    public List<Map<String,String>> getLogs(@RequestBody SearchFilter searchFilter){
        try {
            return auditabilityService.getLogs(searchFilter);
        }
        catch (ResourceAccessException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/csv")
    public String exportLogsToCvs(@RequestBody Export export){
        try {
            return auditabilityService.exportLogsToCSV(export);
        }
        catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
