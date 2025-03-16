package com.smartitax.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AuditLoggerService {

    private final List<Map<String, Object>> auditTrail = new ArrayList<>();

    public void logAuditField(String sessionId, String label, double value, String source, String accuracy, String comment) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("sessionId", sessionId);
        entry.put("label", label);
        entry.put("value", value);
        entry.put("source", source);
        entry.put("accuracy", accuracy);
        entry.put("comment", comment);
        entry.put("timestamp", new Date());
        auditTrail.add(entry);
    }

    public List<Map<String, Object>> getAuditTrail(String sessionId) {
        List<Map<String, Object>> sessionLogs = new ArrayList<>();
        for (Map<String, Object> log : auditTrail) {
            if (log.get("sessionId").equals(sessionId)) {
                sessionLogs.add(log);
            }
        }
        return sessionLogs;
    }
}