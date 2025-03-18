package com.smartitax.controller;

import com.smartitax.model.TaxRequest;
import com.smartitax.model.TaxResponse;
import com.smartitax.service.AuditLoggerService;
import com.smartitax.service.TaxWorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TaxFilingController {

    private final TaxWorkflowService workflowService;
    private final AuditLoggerService auditLoggerService;

    public TaxFilingController(TaxWorkflowService workflowService, AuditLoggerService auditLoggerService) {
        this.workflowService = workflowService;
        this.auditLoggerService = auditLoggerService;
    }

    @PostMapping("/start-session")
    public ResponseEntity<?> startSession(@RequestBody Map<String, String> request) {
        System.out.println("Entering start-session API call with session_id : " + request.get("session_id"));
        String sessionId = request.get("session_id");
        workflowService.startSession(sessionId);
        return ResponseEntity.ok(Map.of("status", "New session started", "session_id", sessionId));
    }

    @PostMapping("/validate-extractions")
    public ResponseEntity<?> validateExtractions(@RequestBody Map<String, Object> request) {
        System.out.println("Entering validate-extractions API call with session_id : " + request.get("session_id"));
        return ResponseEntity.ok(workflowService.enforceGatingCheckpoint(
                (String) request.get("session_id"), true, true));
    }

    @PostMapping("/calculate-tax")
    public ResponseEntity<TaxResponse> calculateTax(@RequestBody TaxRequest request) {
        System.out.println("Entering validate-extractions API call with session_id : " + request.getSession_id());
        double totalIncome = request.getIncome().values().stream().mapToDouble(Double::doubleValue).sum();
        double taxDue = totalIncome * 0.2;
        return ResponseEntity.ok(new TaxResponse(taxDue, 1000, 0.20));
    }

    @PostMapping("/audit-log-save")
    public ResponseEntity<?> saveAudit(@RequestBody Map<String, Object> auditLog) {
        System.out.println("Entering audit-log-save API call with session_id : " + auditLog.get("session_id"));
        return ResponseEntity.ok(Map.of("status", "logged"));
    }

    /*
      End Session - Clear session data
      1. User Prompt to do that
      2. Session timeout after 1 hr of inactive User session
     */
    @PostMapping("/end-session")
    public ResponseEntity<?> endSession(@RequestBody Map<String, String> request) {
        System.out.println("Entering audit-log-save API call with session_id : " + request.get("session_id"));
        String sessionId = request.get("session_id");
        workflowService.endSession(sessionId);
        return ResponseEntity.ok(Map.of("status", "Session ended", "session_id", sessionId));
    }
}