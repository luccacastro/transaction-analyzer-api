## Transaction Analyzer API

This REST API, built with Spring Boot, analyzes transactions based on categories and time periods. It allows users to fetch transaction data, calculate totals, find the highest and lowest transactions, and determine monthly averages. The API supports both custom date ranges and structured yearly analysis for flexible reporting.

Designed for real-world scenarios, the API provides tools to explore transaction patterns over specific seasons, annual reports, or any custom-defined period. This flexibility ensures you can derive actionable insights tailored to your requirements.

---

### Key Features

You can:
- Retrieve all transactions for a specific category.
- Calculate the total outgoing amount for a category within a date range.
- Find the minimum or maximum transaction for a category, either within a date range or for a specific year.
- Calculate the monthly average spending for a category in any year.

The API supports both range-based and yearly queries:
- **Range-based queries** allow flexible analysis, such as focusing on a quarter or a custom period.
- **Yearly queries** provide structured reports, ideal for budget reviews or summaries.


The API includes both date range and yearly queries to cater to diverse analytical needs. While yearly queries provide a straightforward way to generate structured reports for an entire year, range-based queries offer the flexibility to zoom into specific periods, such as seasons or custom timeframes. For instance:

- If you’re looking to identify the highest spend over a summer season, a range query allows this granularity.
- Conversely, if you’re reviewing an annual report, a yearly query simplifies fetching totals, maximums, or minimums for the year.

This combination of flexibility and structure ensures the API meets a wide range of use cases, from ad-hoc analysis to scheduled reporting.

---

### Requirements

To run the project, you need:
- Java 17 or higher
- Maven
- Spring Boot 3.3.5
- Swagger/OpenAPI v3
- JUnit 5 for testing

Optional tools:
- Postman or cURL for testing the API
- Docker for containerization

---

### Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-repo/transaction-analyzer.git
   cd transaction-analyzer
   ```

2. **Build the Project:**
   ```bash
   mvn clean package
   ```

3. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API:**
   Open your browser and navigate to:
   ```
   http://localhost:8080
   ```

---

### API Documentation

Swagger provides an interactive interface to explore and test the API. Access Swagger UI by navigating to:
```http
http://localhost:8080/swagger-ui/index.html
```

Swagger includes all available endpoints, descriptions, and example responses. You can test queries directly from the interface.

---

### Running Tests

This project uses **JUnit 5** for testing, ensuring reliable behavior and proper error handling.

Run the tests with:
```bash
mvn test
```

Tests cover:
- Successful responses for valid queries.
- Handling invalid date ranges.
- Proper exceptions for missing categories or empty results.

---

### Project Architecture

The project follows a clean and modular design:

- **Controllers:** Handle API requests and route them to appropriate services.
- **Services:** Implement business logic, such as transaction filtering and calculations.
- **DTOs:** Standardize API responses for easy consumption.
- **Repositories:** Manage transaction data, including filtering by category or date.
- **Custom Exceptions:** Provide clear error messages for invalid input or missing data.
- **Swagger Integration:** Auto-generates API documentation for easy exploration.

---

### Final Notes

This API is ready for production with its thorough documentation, robust tests, and flexible query options. Whether you’re analyzing annual budgets or specific seasonal trends, this tool adapts to your needs while being easy to extend with new features or integrations.

