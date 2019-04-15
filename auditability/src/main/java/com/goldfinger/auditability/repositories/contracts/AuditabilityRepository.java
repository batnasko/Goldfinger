package com.goldfinger.auditability.repositories.contracts;

import com.goldfinger.auditability.models.Filter;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AuditabilityRepository {
    long addNewLog();

    Map<String, String> getLog(long logId);

    boolean addKeyValuePair(long logId, String key, String value);

    long getWordId(String word);

    long addWord(String word);

    boolean addWordLogRelation(long wordId, long logId);

    List<Integer> getAllLogs(Filter filter);

    Set<Integer> wordOccurrences(String wordsToSearch);

    List<Integer> searchPhrase(String phrase, Filter filter);

    List<Integer> searchWordInPair(String key, String value, Filter filter);

    List<Integer> searchExactTextInPairs(String key, String value, Filter filter);


}
