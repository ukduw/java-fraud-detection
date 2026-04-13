package com.ed.transaction.controller;

import com.ed.transaction.model.TransactionRecord;
import com.ed.transaction.repository.TransactionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionRepository repo;

    public TransactionController(TransactionRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<TransactionRecord> getAll() {
        return repo.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<TransactionRecord> getByUser(@PathVariable String userId) {
        return repo.findByUserId(userId);
    }
}
