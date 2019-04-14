package com.goldfinger.auditability.services;


import com.goldfinger.auditability.models.Export;
import com.goldfinger.auditability.models.SearchFilter;
import com.goldfinger.auditability.repositories.contracts.AuditabilityRepository;
import com.goldfinger.auditability.services.contracts.AuditabilityService;
import com.goldfinger.auditability.services.helpers.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        throw new NotImplementedException();
    }

    @Override
    public String exportLogsToCSV(Export export) {
        if (export.getColumnsToExport() == null || export.getColumnsToExport().length == 0) {
            throw new IllegalArgumentException(NO_COLUMNS_TO_EXPORT);
        }
        return parser.logsToCSV(getLogs(export.getSearchFilter()), export.getColumnsToExport());
    }
}
