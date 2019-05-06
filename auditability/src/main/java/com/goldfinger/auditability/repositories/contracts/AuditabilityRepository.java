package com.goldfinger.auditability.repositories.contracts;

import com.goldfinger.auditability.models.Filter;

import java.util.*;

public interface AuditabilityRepository {
    long addNewLog(String log);

    Map<String, String> getLog(long logId);

    boolean addKeyValuePair(long logId, String key, String value);

    List<Integer> getAllLogs(Filter filter);

    List<Integer> searchPhrase(String phrase, Filter filter);

    List<Integer> searchWordInPair(String key, String value, Filter filter);

    List<Integer> searchExactTextInPairs(String key, String value, Filter filter);

    List<Integer> fullTextSearch(String search);


}
