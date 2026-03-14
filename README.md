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

```
BankAccountManagement/
├── src/com/bank
│   ├── account
│   │   ├── Account.java
│   │   ├── CheckingAccount.java
│   │   ├── SavingsAccount.java
│   │
│   ├── customer
│   │   ├── Customer.java
│   │   ├── PremiumCustomer.java
│   │   ├── RegularCustomer.java
│   │
│   ├── management
│   │   ├── AccountManager.java
│   │   ├── TransactionManager.java
│   │
│   ├── transaction
│   │   ├── Transactable.java
│   │   ├── Transaction.java
│   │
│   ├── ui
│   │   ├── MenuHandler.java
│   │
│   └── Main.java
│
├── README.md
└── .gitignore
```

---

## Technologies Used

* **Java**
* **Object-Oriented Programming (OOP)**
* **Console-based UI**

---

## Sample Output
### Screenshot 1: Main Menu
<img width="616" height="220" alt="image" src="https://github.com/user-attachments/assets/2066612c-ca71-425d-a67d-49204a4aeffc" />

### Screenshot 2: Account Creation (Savings account)
<img width="624" height="643" alt="Screenshot 2026-03-10 154420" src="https://github.com/user-attachments/assets/0db5df6a-9eaa-4253-98ba-eaa74c178b67" />

### Screenshot 3: Account Creation (Checking account)
<img width="653" height="646" alt="Screenshot 2026-03-10 154407" src="https://github.com/user-attachments/assets/b74fa474-b556-4713-8f1b-f75b83877eaa" />

### Screenshot 4: View Accounts
<img width="680" height="550" alt="Screenshot 2026-03-10 154324" src="https://github.com/user-attachments/assets/6a08dc14-66c9-4ed4-a580-1ba8a23c47ac" />

### Screenshot 5: Process Transaction (Deposit)
<img width="540" height="701" alt="Screenshot 2026-03-11 122934" src="https://github.com/user-attachments/assets/c7b54ae5-124d-4d17-93de-d9f4a3078659" />


### Screenshot 6: Process Transaction (Withdrawal)
<img width="642" height="701" alt="image" src="https://github.com/user-attachments/assets/a229c8da-8b0b-43a8-a2f2-5cba3cb1ea39" />


### Screenshot 7: View Transaction History
<img width="742" height="480" alt="image" src="https://github.com/user-attachments/assets/f8c056a1-94c4-468b-9a0c-6f8bd4abdb6f" />


### Screenshot 8: Exit with a (Good Bye message)
<img width="472" height="59" alt="Screenshot 2026-03-10 154906" src="https://github.com/user-attachments/assets/4cc0f004-9693-420b-9924-f41757e557dc" />


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

```
git clone (https://github.com/1MuhireDavid/BankManagementApp.git)
```

2. Navigate to the project folder

```
cd BankAccountManagement
```

3. Compile the project

```
javac src\com\bank\Main.java
```

4. Run the application

```
java com.bank.Main
```

---

## Recommended development tools

* IntelliJ Idea

---

## Author

Muhire David
GitHub: https://github.com/1MuhireDavid
