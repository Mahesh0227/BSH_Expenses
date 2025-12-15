package com.example.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomeDTO {

    private Long id;        // present for responses
    private Long userId;
    private String source;
    private LocalDate date;
    private BigDecimal amount;

    public IncomeDTO() {}

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
