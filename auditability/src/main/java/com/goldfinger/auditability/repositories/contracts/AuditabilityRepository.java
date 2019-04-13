package com.goldfinger.auditability.repositories.contracts;

public interface AuditabilityRepository {
    long addNewLog();
    boolean addKeyValuePair(long logId, String key, String value);
    long getWordId(String word);
    long addWord(String word);
    boolean addWordLogRelation(long wordId, long logId);
}
