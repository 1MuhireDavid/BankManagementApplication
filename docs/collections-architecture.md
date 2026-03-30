# Collections Architecture

This document describes how Java Collections are leveraged in the Bank Management Application to provide efficient data management and processing.

## 1. Data Structures

### Account Management (`AccountService`)
We use a `HashMap<String, Account>` to store bank accounts.
- **Key**: Account Number (`String`).
- **Value**: `Account` object.
- **Rationale**: Provides $O(1)$ time complexity for common operations like finding an account by its number, which happens frequently during transactions and customer updates.

### Transaction History (`TransactionService`)
All transactions are stored in an `ArrayList<Transaction>`.
- **Structure**: `List<Transaction>`.
- **Rationale**: Transactions are naturally chronological. An `ArrayList` provides efficient appending ($O(1)$ amortized) and maintains the insertion order, which is crucial for displaying transaction history.

## 2. Functional Processing (`FunctionalUtils`)

The application embraces functional programming paradigms using Java Streams and Lambdas to process collections.

- **Filtering**: Streams are used to filter transactions by account number or type.
- **Sorting**: We use `Comparator` with Streams to sort transactions (e.g., newest-first, by amount) and accounts (e.g., by balance).
- **Aggregations**: `mapToDouble` and `sum()` are used to calculate total balances across accounts or total transaction amounts.
- **Grouping**: `Collectors.groupingBy` is used to categorize transactions by type.

## 3. Persistence Layer (`FilePersistenceService`)

Data persistence also leverages modern collection features:
- **Streams**: When saving, collections are converted to streams, transformed into formatted strings, and collected into lists for writing.
- **NIO Integration**: `Files.lines()` provides a stream of lines from the data files, which is processed functionally to reconstruct account and transaction objects.

## 4. Summary of Benefits
- **Performance**: Optimal lookup times for accounts.
- **Maintainability**: Clean, declarative code for data processing using the Streams API.
- **Flexibility**: Easy to add new filtering or sorting criteria without modifying the underlying data structures.
