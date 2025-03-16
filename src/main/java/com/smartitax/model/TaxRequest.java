package com.smartitax.model;

import java.util.Map;

public class TaxRequest {
    private String session_id;
    private String taxpayer_name;
    private Map<String, Double> income;
    private Map<String, Double> deductions;
    private Map<String, Double> credits;

    // Getters and Setters
    public String getSession_id() { return session_id; }
    public void setSession_id(String session_id) { this.session_id = session_id; }
    public String getTaxpayer_name() { return taxpayer_name; }
    public void setTaxpayer_name(String taxpayer_name) { this.taxpayer_name = taxpayer_name; }
    public Map<String, Double> getIncome() { return income; }
    public void setIncome(Map<String, Double> income) { this.income = income; }
    public Map<String, Double> getDeductions() { return deductions; }
    public void setDeductions(Map<String, Double> deductions) { this.deductions = deductions; }
    public Map<String, Double> getCredits() { return credits; }
    public void setCredits(Map<String, Double> credits) { this.credits = credits; }
}