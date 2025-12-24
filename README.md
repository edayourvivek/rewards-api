# Rewards API

A Spring Boot REST API that calculates customer reward points based on transaction amounts over a rolling three-month period.

---

## Problem Statement

Customers earn reward points based on individual transactions using the following rules:

- **2 points** for every dollar spent **over $100**
- **1 point** for every dollar spent **between $50 and $100**
- Rewards are calculated **per transaction**
- Monthly rewards and total rewards are aggregated **per customer**

### Example
A $120 transaction earns:
- (120 − 100) × 2 = 40 points
- (100 − 50) × 1 = 50 points  
  **Total = 90 points**

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- H2 In-Memory Database
- Lombok
- JUnit
- Maven

---

## API Endpoint

- **GET** `/api/rewards`  
  Returns reward points aggregated per customer and per month for the last three months.


## Assumptions

- Rewards are calculated for transactions within the last **3 months** from the current date.
- Transactions with missing `customerId`, `amount`, or `date` are **ignored** during reward calculation.
- Reward points are calculated per transaction and then aggregated monthly and per customer.

## How to Run

### Run the application
```bash
./mvnw spring-boot:run
````
### Run test
```bash
./mvnw test
```

