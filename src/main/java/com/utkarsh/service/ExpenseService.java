package com.utkarsh.service;

import com.utkarsh.entity.Expense;

import java.util.List;

public interface ExpenseService {

    Expense addExpense(Long userId, Expense expense);
    List<Expense> getExpensesByUser(Long userId);
}
