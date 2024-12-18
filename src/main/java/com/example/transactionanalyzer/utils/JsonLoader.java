package com.example.transactionanalyzer.utils;

import com.example.transactionanalyzer.model.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class JsonLoader {
    public static List<Transaction> loadTransactionsFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(
                    Paths.get(filePath).toFile(),
                    new TypeReference<List<Transaction>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load transactions from JSON", e);
        }
    }
}
