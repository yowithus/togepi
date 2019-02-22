package com.example.togepi.service;

import com.example.togepi.model.Category;
import com.example.togepi.model.Expense;
import com.example.togepi.model.PaymentMethod;
import com.example.togepi.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface MainService {

    String hello();

    List<Category> getCategories();

    Category getCategoryById(Long categoryId) throws ResourceNotFoundException;

    Category createCategory(Category category);

    Category updateCategory(Long categoryId, Category category) throws ResourceNotFoundException;

    Map<String, Boolean> deleteCategory(Long categoryId) throws ResourceNotFoundException;

    List<PaymentMethod> getPaymentMethods();

    PaymentMethod getPaymentMethodById(Long paymentMethodId) throws ResourceNotFoundException;

    PaymentMethod createPaymentMethod(PaymentMethod paymentMethod);

    PaymentMethod updatePaymentMethod(Long paymentMethodId, PaymentMethod paymentMethod) throws ResourceNotFoundException;

    Map<String, Boolean> deletePaymentMethod(Long paymentMethodId) throws ResourceNotFoundException;

    List<Expense> getExpenses();

    Expense getExpenseById(Long expenseId) throws ResourceNotFoundException;

    Expense createExpense(Expense expense) throws ResourceNotFoundException;

    Expense updateExpense(Long expenseId, Expense expense) throws ResourceNotFoundException;

    Map<String, Boolean> deleteExpense(Long expenseId) throws ResourceNotFoundException;
}
