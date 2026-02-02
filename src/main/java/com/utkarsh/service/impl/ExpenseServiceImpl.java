package com.utkarsh.service.impl;

import com.utkarsh.dto.ExpenseResponseDto;
import com.utkarsh.entity.Expense;
import com.utkarsh.entity.User;
import com.utkarsh.repository.ExpenseRepository;
import com.utkarsh.repository.UserRepository;
import com.utkarsh.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


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
    public Page<ExpenseResponseDto> getExpensesByUser(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("expenseDate").descending());

        Page<Expense> expensePage = expenseRepository.findByUserId(userId, pageable);
        return expensePage.map(expense ->
                new ExpenseResponseDto(
                        expense.getId(),
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getExpenseDate()
                ));

    }
}
