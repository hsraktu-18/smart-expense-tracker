package com.utkarsh.service.impl;

import com.utkarsh.entity.Expense;
import com.utkarsh.entity.User;
import com.utkarsh.repository.ExpenseRepository;
import com.utkarsh.repository.UserRepository;
import com.utkarsh.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Expense addExpense(Long userId, Expense expense) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getExpensesByUser(Long userId) {
        return expenseRepository.findByUserId(userId);
    }
}
