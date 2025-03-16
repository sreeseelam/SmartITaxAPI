package com.smartitax.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaxWorkflowService {

    private final Set<String> validSessionIds = new HashSet<>();
    private final Map<String, Long> sessionAccessMap = new HashMap<>();

    public boolean startSession(String sessionId) {
        validSessionIds.add(sessionId);
        sessionAccessMap.put(sessionId, System.currentTimeMillis());
        return true;
    }

    public boolean isValidSession(String sessionId) {
        return validSessionIds.contains(sessionId);
    }

    public Map<String, Object> enforceGatingCheckpoint(String sessionId, boolean allDocsUploaded, boolean summaryConfirmed) {
        updateSessionActivity(sessionId);
        Map<String, Object> result = new HashMap<>();
        if (!validSessionIds.contains(sessionId)) {
            result.put("status", "fail");
            result.put("message", "Invalid or expired session");
        } else if (!allDocsUploaded) {
            result.put("status", "hold");
            result.put("message", "Awaiting all documents upload confirmation");
        } else if (!summaryConfirmed) {
            result.put("status", "hold");
            result.put("message", "User must validate extraction summary first");
        } else {
            result.put("status", "pass");
            result.put("message", "Gate checkpoint passed");
        }
        return result;
    }

    public void endSession(String sessionId) {
        validSessionIds.remove(sessionId);
        sessionAccessMap.remove(sessionId);
    }

    public void updateSessionActivity(String sessionId) {
        sessionAccessMap.put(sessionId, System.currentTimeMillis());
    }

    @Scheduled(fixedRate = 600000) // Every 10 minutes
    public void cleanupInactiveSessions() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, Long>> it = sessionAccessMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            if ((now - entry.getValue()) > 2 * 60 * 60 * 1000) {
                System.out.println("Session expired due to inactivity: " + entry.getKey());
                validSessionIds.remove(entry.getKey());
                it.remove();
            }
        }
    }
}