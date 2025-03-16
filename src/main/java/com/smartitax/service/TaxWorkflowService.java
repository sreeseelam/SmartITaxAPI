package com.smartitax.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TaxWorkflowService {

    private final Set<String> validSessionIds = new HashSet<>();

    public boolean startSession(String sessionId) {
        validSessionIds.add(sessionId);
        return true;
    }

    public boolean isValidSession(String sessionId) {
        return validSessionIds.contains(sessionId);
    }

    public Map<String, Object> enforceGatingCheckpoint(String sessionId, boolean allDocsUploaded, boolean summaryConfirmed) {
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
    }
}