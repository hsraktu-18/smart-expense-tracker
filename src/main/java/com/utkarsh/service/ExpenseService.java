package com.utkarsh.service;

import com.utkarsh.dto.ExpenseResponseDto;
import com.utkarsh.entity.Expense;
import org.springframework.data.domain.Page;

public interface ExpenseService {

    Expense addExpense(Long userId, Expense expense);
    Page<ExpenseResponseDto> getExpensesByUser(Long userId, int page, int size);
}
