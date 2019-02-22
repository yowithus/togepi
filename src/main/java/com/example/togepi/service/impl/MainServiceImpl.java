package com.example.togepi.service.impl;

import com.example.togepi.model.Category;
import com.example.togepi.model.Expense;
import com.example.togepi.model.PaymentMethod;
import com.example.togepi.service.MainService;
import com.example.togepi.exception.ResourceNotFoundException;
import com.example.togepi.repository.CategoryRepository;
import com.example.togepi.repository.ExpenseRepository;
import com.example.togepi.repository.PaymentMethodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MainServiceImpl implements MainService {
    private static final Logger LOG = LoggerFactory.getLogger(MainServiceImpl.class.getName());

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public String hello() {
        return "hello2";
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) throws ResourceNotFoundException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, Category categoryRequest) throws ResourceNotFoundException {
        return categoryRepository.findById(categoryId).map(category -> {
            category.setName(categoryRequest.getName());
            category.setDescription(categoryRequest.getDescription());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Map<String, Boolean> deleteCategory(Long categoryId) throws ResourceNotFoundException {
        return categoryRepository.findById(categoryId).map(category -> {
            categoryRepository.delete(category);

            final Map<String, Boolean> response = new HashMap<>();
            response.put("isDeleted", Boolean.TRUE);
            return response;
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod getPaymentMethodById(Long paymentMethodId) throws ResourceNotFoundException {
        return paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found"));
    }

    @Override
    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod updatePaymentMethod(Long paymentMethodId, PaymentMethod paymentMethodRequest) throws ResourceNotFoundException {
        return paymentMethodRepository.findById(paymentMethodId).map(paymentMethod -> {
            paymentMethod.setName(paymentMethodRequest.getName());
            paymentMethod.setDescription(paymentMethodRequest.getDescription());
            return paymentMethodRepository.save(paymentMethod);
        }).orElseThrow(() -> new ResourceNotFoundException("Payment method not found"));
    }

    @Override
    public Map<String, Boolean> deletePaymentMethod(Long paymentMethodId) throws ResourceNotFoundException {
        return paymentMethodRepository.findById(paymentMethodId).map(paymentMethod -> {
            paymentMethodRepository.delete(paymentMethod);

            final Map<String, Boolean> response = new HashMap<>();
            response.put("isDeleted", Boolean.TRUE);
            return response;
        }).orElseThrow(() -> new ResourceNotFoundException("Payment method not found"));
    }

    @Override
    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense getExpenseById(Long expenseId) throws ResourceNotFoundException {
        return expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
    }

    @Override
    public Expense createExpense(Expense expense) throws ResourceNotFoundException {
        final Long categoryId = Optional.of(expense.getCategory().getId()).orElseThrow(() -> new IllegalArgumentException());
        final Long paymentMethodId = Optional.of(expense.getPaymentMethod().getId()).orElseThrow(() -> new IllegalArgumentException());

        final Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        final PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow(() -> new ResourceNotFoundException("Payment method not found"));

        expense.setCategory(category);
        expense.setPaymentMethod(paymentMethod);
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long expenseId, Expense expenseRequest) throws ResourceNotFoundException {
        final Long categoryId = Optional.of(expenseRequest.getCategory().getId()).orElseThrow(() -> new IllegalArgumentException());
        final Long paymentMethodId = Optional.of(expenseRequest.getPaymentMethod().getId()).orElseThrow(() -> new IllegalArgumentException());

        final Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        final PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).orElseThrow(() -> new ResourceNotFoundException("Payment method not found"));

        return expenseRepository.findById(expenseId).map(expense -> {
            expense.setName(expenseRequest.getName());
            expense.setDescription(expenseRequest.getDescription());
            expense.setCategory(category);
            expense.setPaymentMethod(paymentMethod);
            return expenseRepository.save(expense);
        }).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
    }

    @Override
    public Map<String, Boolean> deleteExpense(Long expenseId) throws ResourceNotFoundException {
        return expenseRepository.findById(expenseId).map(expense -> {
            expenseRepository.delete(expense);

            final Map<String, Boolean> response = new HashMap<>();
            response.put("isDeleted", Boolean.TRUE);
            return response;
        }).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
    }
}
