package com.example.togepi.controller;

import com.example.togepi.entity.Item;
import com.example.togepi.service.impl.DefaultLockingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LockingController {

    @Autowired
    private DefaultLockingService lockingService;

    @PutMapping(value = "/items/{itemId}")
    public Item updateItem(@PathVariable Long itemId, @Valid @RequestBody Item item) {
        return lockingService.updateItem(itemId, item);
    }
}
