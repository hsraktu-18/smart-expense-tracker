package com.utkarsh.service.impl;

import com.utkarsh.dto.ExpenseResponseDto;
import com.utkarsh.entity.Expense;
import com.utkarsh.entity.User;
import com.utkarsh.repository.ExpenseRepository;
import com.utkarsh.repository.UserRepository;
import com.utkarsh.service.ExpenseService;
import com.utkarsh.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Expense addExpenseForCurrentUser(Expense expense) {

        String email = SecurityUtil.getLoggedInUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        expense.setUser(user);
        return expenseRepository.save(expense);
    }


    @Override
    public List<ExpenseResponseDto> getExpensesForCurrentUser() {

        String email = SecurityUtil.getLoggedInUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Expense> expenses = expenseRepository.findByUserId(user.getId());

        return expenses.stream()
                .map(expense -> new ExpenseResponseDto(
                        expense.getId(),
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getExpenseDate()
                ))
                .toList();
    }

    @Override
    public Page<ExpenseResponseDto> getPagedExpensesForCurrentUser(int page,  int size) {

        String email = SecurityUtil.getLoggedInUserEmail();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("expenseDate").descending()
        );
        Page<Expense> expensePage =
                expenseRepository.findByUserId(user.getId(), pageable);
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
