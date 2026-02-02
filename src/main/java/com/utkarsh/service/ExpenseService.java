package com.utkarsh.service;

import com.utkarsh.dto.ExpenseResponseDto;
import com.utkarsh.entity.Expense;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ExpenseService {

    Expense addExpense(Long userId, Expense expense);

    List<ExpenseResponseDto> getExpensesForCurrentUser();

    Page<ExpenseResponseDto> getPagedExpensesForCurrentUser(int page, int size);
}

