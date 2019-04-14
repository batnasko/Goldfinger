package com.goldfinger.auditability.services;


import com.goldfinger.auditability.repositories.contracts.AuditabilityRepository;
import com.goldfinger.auditability.services.contracts.AuditabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuditabilityServiceImpl implements AuditabilityService {
    private AuditabilityRepository auditabilityRepository;

    @Autowired
    public AuditabilityServiceImpl(AuditabilityRepository auditabilityRepository) {
        this.auditabilityRepository = auditabilityRepository;
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
                }
                catch (IllegalArgumentException e){
                    wordId = auditabilityRepository.addWord(word);
                }
                auditabilityRepository.addWordLogRelation(wordId,logId);
            }
        }
        return true;
    }
}
