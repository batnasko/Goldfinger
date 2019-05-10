package com.goldfinger.auditability.services.contracts;

import com.goldfinger.auditability.models.Export;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AuditabilityService {
    boolean addLog(HashMap<String,String> log);
    List<Map<String,String>> getLogs(String search);
    String exportLogsToCSV(Export export);
}
