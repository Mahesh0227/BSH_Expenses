package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "savings")
public class Savings {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "user_id", nullable = false)
	    private Long userId;

	    @Column(nullable = false)
	    private String details;

	    @Column(nullable = false)
	    private LocalDate date;

	    @Column(nullable = false)
	    private Double amount;

	    @Column(name = "created_at", updatable = false)
	    private LocalDateTime createdAt;

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt;

	    @PrePersist
	    public void onCreate() {
	        createdAt = LocalDateTime.now();
	        updatedAt = createdAt;
	    }

	    @PreUpdate
	    public void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }

    // constructors
    public Savings() {}

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate string) { this.date = string; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
