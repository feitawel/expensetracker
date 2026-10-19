package com.example.expense_tracker.service;

import com.example.expense_tracker.entity.Expense;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @PersistenceContext
    private EntityManager entityManager;

    // CREATE
    @Transactional
    public Expense createExpense(Expense expense) {
        entityManager.persist(expense);
        return expense;
    }

    // READ ALL
    public List<Expense> getAllExpenses() {
        return entityManager.createQuery("SELECT e FROM Expense e", Expense.class)
                .getResultList();
    }

    // READ ONE
    public Optional<Expense> getExpenseById(Long id) {
        return Optional.ofNullable(entityManager.find(Expense.class, id));
    }

    // UPDATE
    @Transactional
    public Expense updateExpense(Long id, Expense expenseDetails) {

        Expense expense = entityManager.find(Expense.class, id);
        if (expense == null) {
            throw new RuntimeException("Expense not found");
        }

        expense.setDescription(expenseDetails.getDescription());
        expense.setAmount(expenseDetails.getAmount());
        expense.setCategory(expenseDetails.getCategory());
        expense.setExpenseDate(expenseDetails.getExpenseDate());
        expense.setPaymentMethod(expenseDetails.getPaymentMethod());

        return expense;
    }

    // DELETE
    @Transactional
    public void deleteExpense(Long id) {

        Expense expense = entityManager.find(Expense.class, id);
        if (expense == null) {
            throw new RuntimeException("Expense not found");
        }

        entityManager.remove(expense);
    }
}