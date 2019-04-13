package com.goldfinger.auditability.services;


import com.goldfinger.auditability.repositories.contracts.AuditabilityRepository;
import com.goldfinger.auditability.services.contracts.AuditabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditabilityServiceImpl implements AuditabilityService {
    private AuditabilityRepository auditabilityRepository;

    @Autowired
    public AuditabilityServiceImpl(AuditabilityRepository auditabilityRepository){
        this.auditabilityRepository = auditabilityRepository;
    }
}
