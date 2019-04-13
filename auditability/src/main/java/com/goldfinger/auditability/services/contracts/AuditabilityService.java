package com.goldfinger.auditability.services.contracts;

import java.util.HashMap;

public interface AuditabilityService {
    boolean addLog(HashMap<String,String> log);
}
