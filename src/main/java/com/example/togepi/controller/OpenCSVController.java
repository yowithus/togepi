package com.example.togepi.controller;

import com.example.togepi.model.Expense;
import com.example.togepi.service.OpenCSVService;
import com.example.togepi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OpenCSVController {

    @Autowired
    private OpenCSVService openCSVService;

    @GetMapping(value = "/csv")
    public List<String[]> read() throws ResourceNotFoundException {
        return openCSVService.read();
    }

    @GetMapping(value = "/csv/object")
    public List<Expense> readToObject() throws ResourceNotFoundException {
        return openCSVService.readToObject();
    }

    @PostMapping(value = "/csv")
    public List<String[]> write() throws ResourceNotFoundException {
        return openCSVService.write();
    }

    @PostMapping(value = "/csv/object")
    public List<Expense> writeFromObject() throws ResourceNotFoundException {
        return openCSVService.writeFromObject();
    }
}
