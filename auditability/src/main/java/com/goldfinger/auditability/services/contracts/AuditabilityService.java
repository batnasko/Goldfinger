package com.goldfinger.auditability.services.contracts;

import com.goldfinger.auditability.models.Export;
import com.goldfinger.auditability.models.SearchFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AuditabilityService {
    boolean addLog(HashMap<String,String> log);
    List<Map<String,String>> getLogs(SearchFilter searchFilter);
    String exportLogsToCSV(Export export);
}
