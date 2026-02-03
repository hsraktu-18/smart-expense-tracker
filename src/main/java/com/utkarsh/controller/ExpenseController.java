package com.utkarsh.controller;

import com.utkarsh.dto.ExpenseResponseDto;
import com.utkarsh.entity.Expense;
import com.utkarsh.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(
             @RequestBody Expense expense){

        Expense savedExpense = expenseService.addExpenseForCurrentUser(expense);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ExpenseResponseDto>> getMyExpenses() {
        return ResponseEntity.ok(expenseService.getExpensesForCurrentUser());
    }

    @GetMapping("/my/paged")
    public ResponseEntity<Page<ExpenseResponseDto>> getMyPagedExpenses(
            @RequestParam int page,
            @RequestParam int size){

        return ResponseEntity.ok(
                expenseService.getPagedExpensesForCurrentUser(page, size));
    }
}
