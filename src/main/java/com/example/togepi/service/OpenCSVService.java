package com.example.togepi.service;

import com.example.togepi.model.Expense;
import com.example.togepi.exception.ResourceNotFoundException;

import java.util.List;

public interface OpenCSVService {

    List<String[]> read() throws ResourceNotFoundException;

    List<Expense> readToObject() throws ResourceNotFoundException;

    List<String[]> write() throws ResourceNotFoundException;

    List<Expense> writeFromObject() throws ResourceNotFoundException;
}
