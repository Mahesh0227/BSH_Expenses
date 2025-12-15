package com.example.dto;

import java.time.LocalDate;

public class ExpensesDto {

    private Long id;
    private Long userId;
    private String expenseDetails;
    private LocalDate date;
    private String payment;
    private Double amount;
    private String attachmentUrl;
    private String warrantyUrl;

    public ExpensesDto() {}

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getExpenseDetails() { return expenseDetails; }
    public void setExpenseDetails(String expenseDetails) { this.expenseDetails = expenseDetails; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getPayment() { return payment; }
    public void setPayment(String payment) { this.payment = payment; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }

    public String getWarrantyUrl() { return warrantyUrl; }
    public void setWarrantyUrl(String warrantyUrl) { this.warrantyUrl = warrantyUrl; }
}
