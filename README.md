## Transaction Analyzer API

This is a REST API built using Spring Boot that helps analyze transactions based on categories, dates, and ranges. It lets you fetch transaction data, calculate totals, find minimum and maximum amounts, and check monthly averages. You can customize your queries using date ranges or specific years, making it flexible for different use cases.

The program is designed to support real-world scenarios. For example, you might need a structured report over a year, or you may want to analyze spending for specific time frames, like a quarter or a busy season. By providing both options, the API ensures that you can get the exact data you need.

---

### What It Does

You can:
- Get all transactions for a specific category.
- Calculate the total outgoing amount within a date range.
- Find the minimum or maximum transaction amount, either within a date range or for a specific year.
- Calculate the monthly average spend for any given year.

The range-based queries are there to give you freedom. Maybe you’re only interested in the summer months, or you want to focus on the first quarter of the year. On the other hand, the yearly endpoints are perfect for structured reporting, like annual reviews.

---

### Why the Mix of Ranges and Yearly Queries?

Not all analysis needs to stick to a single calendar year. The range-based queries are there when you want flexibility, like checking transactions over summer months or the last 60 days. On the other hand, the yearly endpoints make structured analysis simpler, perfect for annual reports or budget reviews.

For example:
- **Yearly Maximum Spend:** Ideal for identifying the biggest purchase of the year.
- **Range-Based Minimum Spend:** Useful for zooming in on custom periods, like quarterly analysis or specific events.

Both approaches complement each other to provide powerful insights.

---

### Requirements

To run the project, you need:
- Java 17 or higher
- JUnit 5
- Maven
- Spring Boot 3.3.5
- Swagger v3
- OpenAPI

Optional tools:
- Postman or cURL for testing the API manually

---

### How to Run the Application

1. **Clone the project**
   ```bash
   git clone https://github.com/your-repo/transaction-analyzer.git
   cd transaction-analyzer
   ```
2. **Build the project**
   ```bash
   mvn clean package
   ```
3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
4. The API will be available at:
   ```
   http://localhost:8080
   ```

---

### How to Use the API

You can explore the API through **Swagger UI**. Swagger provides a simple, interactive interface to test and document the endpoints.

1. Start the app (see steps above).
2. Open your browser and visit:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```
3. From here, you can:
    - View all available endpoints.
    - Send requests directly through the interface.
    - See example responses for each route.

Here are some examples of API calls:

**Get all transactions for a category:**
```bash
curl -X GET "http://localhost:8080/api/transactions/categories/electronics"
```

**Get the total outgoing for a category within a range:**
```bash
curl -X GET "http://localhost:8080/api/transactions/categories/electronics/total?startDate=2024-01-01&endDate=2024-06-01"
```

**Find the maximum spend for a year:**
```bash
curl -X GET "http://localhost:8080/api/transactions/categories/electronics/max/2024"
```

**Find the minimum spend for a specific date range:**
```bash
curl -X GET "http://localhost:8080/api/transactions/categories/electronics/min/range?startDate=2024-01-01&endDate=2024-06-01"
```

---

### Running Tests

The project uses **JUnit 5** for testing. Unit tests ensure all endpoints behave as expected, including edge cases like invalid inputs or empty results. If something breaks, tests will catch it.

To run the tests, use this command:
```bash
mvn test
```

The tests check for:
- Successful responses for valid requests.
- Handling of invalid date ranges.
- Proper exceptions for missing categories.

---

### How It’s Structured

The code is organized to be clean and maintainable:
- **Controllers**: Handle incoming API requests and map them to the correct service.
- **Services**: Contain business logic, such as filtering transactions or calculating totals.
- **DTOs**: Data Transfer Objects standardize responses so they’re easy to work with.
- **Repositories**: Manage data filtering, like categories or date ranges.
- **Exceptions**: Custom exceptions return clear error messages when things go wrong.
- **Swagger**: Automatically generates interactive API documentation.

The transaction data is mocked using a `transactions.json` file for simplicity. You can replace it with a real database in the future.

---


---



