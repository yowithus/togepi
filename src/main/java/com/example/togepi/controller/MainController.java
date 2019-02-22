package com.example.togepi.controller;

import com.example.togepi.exception.ResourceNotFoundException;
import com.example.togepi.model.Category;
import com.example.togepi.model.Expense;
import com.example.togepi.model.PaymentMethod;
import com.example.togepi.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping(value = "/hello")
    public String hello() {
        return mainService.hello();
    }

    @GetMapping(value = "/categories")
    public List<Category> getCategories() {
        return mainService.getCategories();
    }

    @GetMapping(value = "/categories/{categoryId}")
    public Category getCategoryById(@PathVariable Long categoryId) throws ResourceNotFoundException {
        return mainService.getCategoryById(categoryId);
    }

    @PostMapping(value = "/categories")
    public Category createCategory(@Valid @RequestBody Category category) {
        return mainService.createCategory(category);
    }

    @PutMapping(value = "/categories/{categoryId}")
    public Category updateCategory(@PathVariable Long categoryId, @Valid @RequestBody Category category) throws ResourceNotFoundException {
        return mainService.updateCategory(categoryId, category);
    }

    @DeleteMapping(value = "/categories/{categoryId}")
    public Map<String, Boolean> deleteCategory(@PathVariable Long categoryId) throws ResourceNotFoundException {
        return mainService.deleteCategory(categoryId);
    }

    @GetMapping(value = "/payment-methods")
    public List<PaymentMethod> getPaymentMethods() {
        return mainService.getPaymentMethods();
    }

    @GetMapping(value = "/payment-methods/{paymentMethodId}")
    public PaymentMethod getPaymentMethodById(@PathVariable Long paymentMethodId) throws ResourceNotFoundException {
        return mainService.getPaymentMethodById(paymentMethodId);
    }

    @PostMapping(value = "/payment-methods")
    public PaymentMethod createPaymentMethod(@Valid @RequestBody PaymentMethod paymentMethod) {
        return mainService.createPaymentMethod(paymentMethod);
    }

    @PutMapping(value = "/payment-methods/{paymentMethodId}")
    public PaymentMethod updatePaymentMethod(@PathVariable Long paymentMethodId, @Valid @RequestBody PaymentMethod paymentMethod) throws ResourceNotFoundException {
        return mainService.updatePaymentMethod(paymentMethodId, paymentMethod);
    }

    @DeleteMapping(value = "/payment-methods/{paymentMethodId}")
    public Map<String, Boolean> deletePaymentMethod(@PathVariable Long paymentMethodId) throws ResourceNotFoundException {
        return mainService.deletePaymentMethod(paymentMethodId);
    }

    @GetMapping(value = "/expenses")
    public List<Expense> getExpenses() {
        return mainService.getExpenses();
    }

    @GetMapping(value = "/expenses/{expenseId}")
    public Expense getExpenseById(@PathVariable Long expenseId) throws ResourceNotFoundException {
        return mainService.getExpenseById(expenseId);
    }

    @PostMapping(value = "/expenses")
    public Expense createExpense(@Valid @RequestBody Expense expense) throws ResourceNotFoundException {
        return mainService.createExpense(expense);
    }

    @PutMapping(value = "/expenses/{expenseId}")
    public Expense updateExpense(@PathVariable Long expenseId, @Valid @RequestBody Expense expense) throws ResourceNotFoundException {
        return mainService.updateExpense(expenseId, expense);
    }

    @DeleteMapping(value = "/expenses/{expenseId}")
    public Map<String, Boolean> deleteExpense(@PathVariable Long expenseId) throws ResourceNotFoundException {
        return mainService.deleteExpense(expenseId);
    }
}
