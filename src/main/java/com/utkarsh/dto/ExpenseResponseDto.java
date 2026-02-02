package com.utkarsh.dto;

import java.time.LocalDate;

public class ExpenseResponseDto {

    private Long id;
    private String title;
    private Double amount;
    private String category;
    private LocalDate expenseDate;

    public ExpenseResponseDto(Long id, String title, Double amount, String category, LocalDate expenseDate) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }
}
