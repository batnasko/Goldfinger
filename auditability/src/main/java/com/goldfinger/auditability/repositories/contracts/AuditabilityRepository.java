package com.goldfinger.auditability.repositories.contracts;


import java.util.*;

public interface AuditabilityRepository {
    long addNewLog(String log);

    Map<String, String> getLog(long logId);

    boolean addKeyValuePair(long logId, String key, String value);

    List<Integer> getAllLogs();

    List<Integer> searchPhrase(String phrase);

    List<Integer> searchWordInPair(String key, String value);

    List<Integer> searchExactTextInPairs(String key, String value);

    List<Integer> fullTextSearch(String search);


}
