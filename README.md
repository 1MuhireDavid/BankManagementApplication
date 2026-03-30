# Bank Account Management System

## Overview

The **Bank Account Management** is a Java-based console application designed to simulate core banking operations.
It demonstrates key **Object-Oriented Programming (OOP)** concepts such as **inheritance, abstraction, polymorphism, and interfaces**.

The system allows users to create and manage bank accounts, perform financial transactions, and manage customers through a simple menu-driven interface.

---

## Features

* Create and manage bank accounts
* Support for **Checking Accounts** and **Savings Accounts**
* Customer types: **Regular** and **Premium**
* Deposit and withdraw money
* Transaction tracking
* Account management through a console menu
* Object-oriented design with clear package structure

---

## Project Structure

```text
BankAccountManagement/
├── src/
│   ├── main/java/com/bank
│   │   ├── account/
│   │   ├── customer/
│   │   ├── management/
│   │   ├── transaction/
│   │   ├── ui/
│   │   └── Main.java
│   └── test/java/com/bank
│       └── (tests)
├── docs/
│   └── git-workflow.md
├── pom.xml
├── README.md
└── .gitignore
```

> [!NOTE]
> For our contributor guidelines and Git best practices, please refer to our [Git Workflow Document](docs/git-workflow.md).

---

## Technologies Used

* **Java**
* **Object-Oriented Programming (OOP)**
* **Console-based UI**

---

## Sample Output
### Screenshot 1: Main Menu
<img width="615" height="342" alt="Screenshot 2026-03-31 111624" src="https://github.com/user-attachments/assets/41875ef0-bc4f-43f3-a335-11d01c310f23" />

### Screenshot 2: Account Creation (Savings account)
<img width="796" height="634" alt="image" src="https://github.com/user-attachments/assets/1fa48963-bcb4-47f9-9ef0-64f1eac9bcfd" />
### Screenshot 3: Account Creation (Checking account)
<img width="759" height="743" alt="image" src="https://github.com/user-attachments/assets/fcf83d44-d38d-4f15-84b8-cedec65faae8" />

### Screenshot 4: Generate Account Statements
<img width="680" height="550" alt="Screenshot 2026-03-10 154324" src="https://github.com/user-attachments/assets/6a08dc14-66c9-4ed4-a580-1ba8a23c47ac" />

### Screenshot 5: Process Transaction (Deposit)
<img width="540" height="701" alt="Screenshot 2026-03-11 122934" src="https://github.com/user-attachments/assets/c7b54ae5-124d-4d17-93de-d9f4a3078659" />


### Screenshot 6: Process Transaction (Withdrawal)
<img width="642" height="701" alt="image" src="https://github.com/user-attachments/assets/a229c8da-8b0b-43a8-a2f2-5cba3cb1ea39" />


### Screenshot 7: View Transaction History
<img width="742" height="480" alt="image" src="https://github.com/user-attachments/assets/f8c056a1-94c4-468b-9a0c-6f8bd4abdb6f" />


### Screenshot 8: Exit with a (Good Bye message)
<img width="554" height="185" alt="Screenshot 2026-03-31 111808" src="https://github.com/user-attachments/assets/9b52a60a-6236-4a18-9164-e97a5f38b957" />

---
## OOP Concepts Demonstrated

* **Abstraction** – `Account` and `Customer` abstract classes
* **Inheritance** – Specialized accounts and customer types
* **Polymorphism** – Different account behaviors using the same interface
* **Interfaces** – `Transactable` interface for transaction operations
* **Encapsulation** – Private fields with getters and setters

---

## How to Run the Project

1. Clone the repository

```bash
git clone https://github.com/1MuhireDavid/BankManagementApp.git
```

2. Navigate to the project folder

```bash
cd BankAccountManagement
```

3. Build the project using Maven

```bash
mvn clean compile
```

4. Run the application

```bash
mvn exec:java -Dexec.mainClass="com.bank.Main"
```

5. Run unit tests

```bash
mvn test
```

---

## Recommended development tools

* IntelliJ Idea

---

## Author

Muhire David
GitHub: https://github.com/1MuhireDavid
