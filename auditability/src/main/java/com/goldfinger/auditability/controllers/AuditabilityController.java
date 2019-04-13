package com.goldfinger.auditability.controllers;


import com.goldfinger.auditability.services.contracts.AuditabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auditability")
public class AuditabilityController {
    private AuditabilityService auditabilityService;

    @Autowired
    public AuditabilityController(AuditabilityService auditabilityService){
        this.auditabilityService = auditabilityService;
    }
}
