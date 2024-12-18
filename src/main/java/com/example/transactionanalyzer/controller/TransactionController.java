package com.example.transactionanalyzer.controller;

import com.example.transactionanalyzer.dto.*;
import com.example.transactionanalyzer.exceptions.CategoryNotFoundException;
import com.example.transactionanalyzer.exceptions.InvalidDateRangeException;
import com.example.transactionanalyzer.exceptions.UnableToCalculateException;
import com.example.transactionanalyzer.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.example.transactionanalyzer.utils.ErrorMessages.INVALID_DATE_RANGE;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Get all transactions for a given category",
            description = "Returns a list of all transactions associated with the specified category. "
                    + "If the category doesn't exist, a 404 error will be returned.")
    @GetMapping("/categories/{category}")
    public CategoryTransactionsDTO getTransactionsByCategory(
            @Parameter(description = "The category to filter transactions")
            @PathVariable String category
    ) throws CategoryNotFoundException {
        return transactionService.getTransactionsByCategory(category);
    }

    @Operation(summary = "Get total outgoing for a category within a date range",
            description = "Calculates the total outgoing amount for the specified category between the given start and end dates. "
                    + "If no date range is provided, the calculation will include all transactions for the category since 1970.")
    @GetMapping("/categories/{category}/total")
    public CategoryTotalDTO getTotalOutgoingForCategory(
            @Parameter(description = "The category to analyze")
            @PathVariable String category,
            @Parameter(description = "The start date for the calculation (default: 1970-01-01)")
            @RequestParam(required = false, defaultValue = "1970-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "The end date for the calculation (default: today)")
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().toString()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws UnableToCalculateException, InvalidDateRangeException {
        validateDateRange(startDate, endDate);
        return transactionService.getTotalOutgoingForCategory(category, startDate, endDate);
    }

    @Operation(summary = "Get monthly average spend for a category over a year",
            description = "Calculates the monthly average spend for the specified category in the given year. "
                    + "The range is automatically set from January 1st to December 31st of the specified year.")
    @GetMapping("/categories/{category}/monthly-average/{year}")
    public MonthlyAverageDTO getMonthlyAverageSpendForCategory(
            @Parameter(description = "The category to analyze") @PathVariable String category,
            @Parameter(description = "The year for which to calculate monthly averages") @PathVariable String year
    ) throws UnableToCalculateException {
        int parsedYear = Integer.parseInt(year);
        LocalDate startDate = LocalDate.of(parsedYear, 1, 1);
        LocalDate endDate = LocalDate.of(parsedYear, 12, 31);
        return transactionService.getMonthlyAverageSpendForCategory(category, startDate, endDate);
    }

    @Operation(summary = "Get maximum spend for a category within a date range",
            description = "Finds the highest single spend transaction for the specified category between the given start and end dates. "
                    + "If no dates are provided, all transactions for the category since 1970 will be considered.")
    @GetMapping("/categories/{category}/max")
    public MaxAmountDTO getMaxAmountForCategory(
            @Parameter(description = "The category to analyze") @PathVariable String category,
            @Parameter(description = "The start date for the calculation (default: 1970-01-01)")
            @RequestParam(required = false, defaultValue = "1970-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "The end date for the calculation (default: today)")
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().toString()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws UnableToCalculateException, InvalidDateRangeException {
        validateDateRange(startDate, endDate);
        return transactionService.getMaxAmountForCategory(category, startDate, endDate);
    }

    @Operation(summary = "Get minimum spend for a category within a date range",
            description = "Finds the lowest single spend transaction for the specified category between the given start and end dates. "
                    + "If no dates are provided, all transactions for the category since 1970 will be considered.")
    @GetMapping("/categories/{category}/min")
    public MinAmountDTO getMinAmountForCategory(
            @Parameter(description = "The category to analyze") @PathVariable String category,
            @Parameter(description = "The start date for the calculation (default: 1970-01-01)")
            @RequestParam(required = false, defaultValue = "1970-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "The end date for the calculation (default: today)")
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().toString()}") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws UnableToCalculateException, InvalidDateRangeException {
        validateDateRange(startDate, endDate);
        return transactionService.getMinAmountForCategory(category, startDate, endDate);
    }

    @Operation(summary = "Get maximum spend for a category over a year",
            description = "Calculates the maximum spend transaction for the entire specified year.")
    @GetMapping("/categories/{category}/max/{year}")
    public MaxAmountDTO getMaxAmountForCategoryByYear(
            @Parameter(description = "The category to analyze") @PathVariable String category,
            @Parameter(description = "The year over which to find the maximum spend") @PathVariable int year
    ) throws UnableToCalculateException {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return transactionService.getMaxAmountForCategory(category, startDate, endDate);
    }

    @Operation(summary = "Get minimum spend for a category over a year",
            description = "Calculates the minimum spend transaction for the entire specified year.")
    @GetMapping("/categories/{category}/min/{year}")
    public MinAmountDTO getMinAmountForCategoryByYear(
            @Parameter(description = "The category to analyze") @PathVariable String category,
            @Parameter(description = "The year over which to find the minimum spend") @PathVariable int year
    ) throws UnableToCalculateException {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return transactionService.getMinAmountForCategory(category, startDate, endDate);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException(INVALID_DATE_RANGE);
        }
    }
}
