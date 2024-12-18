package com.example.transactionanalyzer;

import com.example.transactionanalyzer.controller.TransactionController;
import com.example.transactionanalyzer.dto.*;
import com.example.transactionanalyzer.exceptions.CategoryNotFoundException;
import com.example.transactionanalyzer.exceptions.InvalidDateRangeException;
import com.example.transactionanalyzer.exceptions.UnableToCalculateException;
import com.example.transactionanalyzer.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionAnalyzerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTransactionsByCategory_ShouldReturnTransactions() throws Exception {
        String category = "electronics";
        CategoryTransactionsDTO response = new CategoryTransactionsDTO(category, Collections.emptyList());

        Mockito.when(transactionService.getTransactionsByCategory(category)).thenReturn(response);

        mockMvc.perform(get("/api/transactions/categories/{category}", category))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(category))
                .andExpect(jsonPath("$.transactions").isArray());
    }

    @Test
    void getTransactionsByCategory_CategoryNotFound() throws Exception {
        String category = "nonexistent";

        Mockito.when(transactionService.getTransactionsByCategory(category))
                .thenThrow(new CategoryNotFoundException("Category not found: " + category));

        mockMvc.perform(get("/api/transactions/categories/{category}", category))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Category not found: nonexistent"));
    }

    @Test
    void getTotalOutgoingForCategory_ShouldReturnTotal() throws Exception {
        String category = "food";
        CategoryTotalDTO response = new CategoryTotalDTO(category, 500.0);

        Mockito.when(transactionService.getTotalOutgoingForCategory(eq(category), any(), any())).thenReturn(response);

        mockMvc.perform(get("/api/transactions/categories/{category}/total", category)
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-06-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("food"));
    }

    @Test
    void getTotalOutgoingForCategory_CategoryNotFound() throws Exception {
        String category = "unknown";

        Mockito.when(transactionService.getTotalOutgoingForCategory(eq(category), any(), any()))
                .thenThrow(new CategoryNotFoundException("Category not found: " + category));

        mockMvc.perform(get("/api/transactions/categories/{category}/total", category))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Category not found: unknown"));
    }

    @Test
    void getMonthlyAverageSpendForCategory_ShouldReturnMonthlyAverages() throws Exception {
        String category = "utilities";
        Map<String, Double> averages = Map.of("2024-01", 100.0, "2024-02", 200.0);
        MonthlyAverageDTO response = new MonthlyAverageDTO(category, "2024", averages);

        Mockito.when(transactionService.getMonthlyAverageSpendForCategory(eq(category), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/transactions/categories/{category}/monthly-average/{year}", category, "2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(category))
                .andExpect(jsonPath("$.year").value("2024"))
                .andExpect(jsonPath("$.monthlyAverages['2024-01']").value(100.0));
    }

    @Test
    void getMaxAmountForCategoryByYear_ShouldReturnMaxAmount() throws Exception {
        String category = "clothing";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        MaxAmountDTO response = new MaxAmountDTO(category, 300.0, startDate, endDate);

        Mockito.when(transactionService.getMaxAmountForCategory(eq(category), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/transactions/categories/{category}/max/{year}", category, "2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(category))
                .andExpect(jsonPath("$.maxAmount").value(300.0));
    }

    @Test
    void getMaxAmountForCategoryByYear_UnableToCalculate() throws Exception {
        String category = "clothing";

        Mockito.when(transactionService.getMaxAmountForCategory(eq(category), any(LocalDate.class), any(LocalDate.class)))
                .thenThrow(new UnableToCalculateException("Unable to calculate max amount for category: " + category));

        mockMvc.perform(get("/api/transactions/categories/{category}/max/{year}", category, "2024"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unable to calculate max amount for category: clothing"));
    }

    @Test
    void getMinAmountForCategoryByYear_ShouldReturnMinAmount() throws Exception {
        String category = "groceries";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        MinAmountDTO response = new MinAmountDTO(category, 50.0, startDate, endDate);

        Mockito.when(transactionService.getMinAmountForCategory(eq(category), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/transactions/categories/{category}/min/{year}", category, "2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(category))
                .andExpect(jsonPath("$.minAmount").value(50.0));
    }

    @Test
    void getMinAmountForCategoryByYear_UnableToCalculate() throws Exception {
        String category = "groceries";

        Mockito.when(transactionService.getMinAmountForCategory(eq(category), any(LocalDate.class), any(LocalDate.class)))
                .thenThrow(new UnableToCalculateException("Unable to calculate min amount for category: " + category));

        mockMvc.perform(get("/api/transactions/categories/{category}/min/{year}", category, "2024"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unable to calculate min amount for category: groceries"));
    }

    @Test
    void getMaxAmountForCategoryByRange_InvalidDateRange() throws Exception {
        mockMvc.perform(get("/api/transactions/categories/{category}/max", "electronics")
                        .param("startDate", "2024-06-01")
                        .param("endDate", "2024-01-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Start date must not be later than the end date."));
    }

    @Test
    void getMaxAmountForCategoryByRange_ShouldReturnMaxAmount() throws Exception {
        String category = "electronics";
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 6, 1);
        MaxAmountDTO response = new MaxAmountDTO(category, 500.0, startDate, endDate);

        Mockito.when(transactionService.getMaxAmountForCategory(eq(category), eq(startDate), eq(endDate)))
                .thenReturn(response);

        mockMvc.perform(get("/api/transactions/categories/{category}/max", category)
                        .param("startDate", "2020-01-01")
                        .param("endDate", "2021-06-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(category))
                .andExpect(jsonPath("$.maxAmount").value(500.0));
    }

    @Test
    void getMinAmountForCategoryByRange_ShouldReturnMinAmount() throws Exception {
        String category = "utilities";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 1);
        MinAmountDTO response = new MinAmountDTO(category, 30.0, startDate, endDate);

        Mockito.when(transactionService.getMinAmountForCategory(eq(category), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/transactions/categories/{category}/min", category)
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-06-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value(category))
                .andExpect(jsonPath("$.minAmount").value(30.0));
    }
}
