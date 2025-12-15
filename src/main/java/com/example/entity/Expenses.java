package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="expense_details", columnDefinition = "TEXT", nullable = false)
    private String expenseDetails;

    @Column(name="date", nullable = false)
    private LocalDate date;

    @Column(name="payment")
    private String payment;

    @Column(name="amount", nullable = false)
    private Double amount;

    @Column(name="attachment_url")
    private String attachmentUrl;

    @Column(name="warranty_url")
    private String warrantyUrl;

    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Expenses() {}

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Optional: lifecycle callbacks to set timestamps
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
