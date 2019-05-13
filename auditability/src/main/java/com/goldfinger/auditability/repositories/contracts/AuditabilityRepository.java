package com.goldfinger.auditability.repositories.contracts;


import java.util.*;

public interface AuditabilityRepository {
    long addNewLog(String log);

    List<Map<String, String>> getLogs(List<Integer> logIds);

    boolean addKeyValues(long logId, Map<String, String> log);

    List<Integer> getAllLogs();

    List<Integer> searchPhrase(String phrase);

    List<Integer> searchWordInPair(String key, String value);

    List<Integer> searchExactTextInPairs(String key, String value);

    List<Integer> fullTextSearch(String search);


}
