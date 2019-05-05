package com.goldfinger.auditability.services;


import com.goldfinger.auditability.models.*;
import com.goldfinger.auditability.repositories.contracts.AuditabilityRepository;
import com.goldfinger.auditability.services.contracts.AuditabilityService;
import com.goldfinger.auditability.services.helpers.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuditabilityServiceImpl implements AuditabilityService {
    private static final String NO_COLUMNS_TO_EXPORT = "Please enter columns to export";

    private AuditabilityRepository auditabilityRepository;
    private Parser parser;

    @Autowired
    public AuditabilityServiceImpl(AuditabilityRepository auditabilityRepository) {
        this.auditabilityRepository = auditabilityRepository;
        this.parser = new Parser();
    }

    @Override
    public boolean addLog(HashMap<String, String> log) {
        long logId = auditabilityRepository.addNewLog();
        for (Map.Entry<String, String> entry : log.entrySet()) {
            auditabilityRepository.addKeyValuePair(logId, entry.getKey(), entry.getValue());
            String[] valueWords = entry.getValue().split(" ");
            for (String word : valueWords) {
                long wordId;
                try {
                    wordId = auditabilityRepository.getWordId(word);
                } catch (IllegalArgumentException e) {
                    wordId = auditabilityRepository.addWord(word);
                }
                auditabilityRepository.addWordLogRelation(wordId, logId);
            }
        }
        return true;
    }

    @Override
    public List<Map<String, String>> getLogs(SearchFilter searchFilter) {
        String search = searchFilter.getSearch();
        List<Integer> logIds = new ArrayList<>();

        if (search == null) {
            logIds = auditabilityRepository.getAllLogs(searchFilter.getFilter());
        } else {
            search = search.trim();

            if (search.startsWith("\"") && search.endsWith("\"")) {
                search = search.substring(1, search.length() - 1);
                logIds = auditabilityRepository.searchPhrase(search, searchFilter.getFilter());
            } else if (isDoubleDotSearch(search)) {
                String[] searchSplit = search.split(":", 2);
                logIds = auditabilityRepository.searchWordInPair(searchSplit[0].trim(), searchSplit[1].trim(), searchFilter.getFilter());
            } else if (isEqualSearch(search)) {
                String[] searchSplit = search.split("=");
                logIds = auditabilityRepository.searchExactTextInPairs(searchSplit[0].trim(), searchSplit[1].trim(), searchFilter.getFilter());
            } else {
                String[] words = search.split(" ");
                Set<Integer> allLogs = new HashSet<>();
                List<List<Integer>> logsOccurrence = new ArrayList<>();
                List<Set<Integer>> logsByWord = new ArrayList<>();
                for (int i = 0; i < words.length; i++) {
                    logsOccurrence.add(new ArrayList<>());
                    logsByWord.add(auditabilityRepository.wordOccurrences(words[i]));
                    allLogs.addAll(logsByWord.get(i));
                }

                for (Integer log : allLogs) {
                    int logCount = 0;
                    for (Set<Integer> logs : logsByWord) {
                        if (logs.contains(log)) {
                            logCount++;
                        }
                    }
                    logsOccurrence.get(logCount - 1).add(log);
                }

                for (int i = logsOccurrence.size() - 1; i >= 0; i--) {
                    logIds.addAll(logsOccurrence.get(i));
                }
            }

        }

        List<Map<String, String>> logs = new ArrayList<>();

        for (Integer logId : logIds) {
            logs.add(auditabilityRepository.getLog(logId));
        }

        return logs;
    }

    @Override
    public String exportLogsToCSV(Export export) {
        if (export.getColumnsToExport() == null || export.getColumnsToExport().length == 0) {
            throw new IllegalArgumentException(NO_COLUMNS_TO_EXPORT);
        }
        return parser.logsToCSV(getLogs(export.getSearchFilter()), export.getColumnsToExport());
    }

    private boolean isEqualSearch(String search) {
        return (search.length() - search.replaceAll("=", "").length()) == 1
                && (search.length() != search.indexOf('=') + 1)
                && (search.indexOf('=') != 0);
    }

    private boolean isDoubleDotSearch(String search) {
        if (!search.startsWith(":") && !search.endsWith(":") && search.contains(":")) {
            String[] searchSplit = search.split(":", 2);
            searchSplit[0] = searchSplit[0].trim();
            searchSplit[1] = searchSplit[1].trim();
            return !Character.isDigit(searchSplit[0].charAt(searchSplit[0].length() - 1)) || !Character.isDigit(searchSplit[1].charAt(0));
        } else return false;
    }
}
