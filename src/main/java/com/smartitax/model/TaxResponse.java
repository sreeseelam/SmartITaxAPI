package com.smartitax.model;

public class TaxResponse {
    private double total_tax_due;
    private double refund_amount;
    private double effective_rate;

    public TaxResponse(double tax, double refund, double rate) {
        this.total_tax_due = tax;
        this.refund_amount = refund;
        this.effective_rate = rate;
    }

    public double getTotal_tax_due() { return total_tax_due; }
    public void setTotal_tax_due(double total_tax_due) { this.total_tax_due = total_tax_due; }
    public double getRefund_amount() { return refund_amount; }
    public void setRefund_amount(double refund_amount) { this.refund_amount = refund_amount; }
    public double getEffective_rate() { return effective_rate; }
    public void setEffective_rate(double effective_rate) { this.effective_rate = effective_rate; }
}