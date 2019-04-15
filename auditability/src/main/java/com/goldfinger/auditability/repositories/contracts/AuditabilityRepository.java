package com.goldfinger.auditability.repositories.contracts;

import java.util.Map;

public interface AuditabilityRepository {
    long addNewLog();
    Map<String,String> getLog(long logId);
    boolean addKeyValuePair(long logId, String key, String value);
    long getWordId(String word);
    long addWord(String word);
    boolean addWordLogRelation(long wordId, long logId);
}
