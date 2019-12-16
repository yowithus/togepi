package com.example.togepi.service;

import com.example.togepi.entity.Order;

import java.util.List;

public interface OpenCsvService {

    List<String[]> read();

    List<Order> readToObject();

    List<String[]> write();

    List<Order> writeFromObject();
}
