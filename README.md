# 🚗 The Intelligent Inventory

## 📌 1. Requirements

* Java 17+
* Maven
* Docker *(optional, recommended)*

---

## 📥 2. Clone Project

```bash
git clone https://github.com/nhutHao02/the-intelligent-inventory.git
cd the-intelligent-inventory
```

---

## 🛠 3. Run Dependencies (MySQL + Redis)

### Option 1: Use Docker *(Recommended)*

```bash
docker compose up -d
```

### Option 2: Run Manually

* MySQL: `localhost:3306`
* Redis: `localhost:6379`

Create database:

```sql
CREATE DATABASE intelligent_inventory_dashboard;

CREATE TABLE dealerships
INSERT INTO dealerships (id, create_at, update_at, address, email, name, phone, status) 
VALUES 
    (1, '2026-05-02 21:39:20', '2026-05-02 21:39:20', 'Ho Chi Minh City, VN', 'dealershipT1@gmail.com', 'Dealership T1', '(+84) 911 225 411', 'ACTIVE'), 
    (2, '2026-05-02 21:39:20', '2026-05-02 21:39:20', 'Ho Chi Minh City, VN', 'dealershipT2@gmail.com', 'Dealership T2', '(+84) 911 123 123', 'ACTIVE'), 
    (3, '2026-05-02 21:39:20', '2026-05-02 21:39:20', 'Ho Chi Minh City, VN', 'dealershipT3@gmail.com', 'Dealership T3', '(+84) 911 321 321', 'ACTIVE');

CREATE TABLE vehicles
INSERT INTO vehicles (
    id, create_at, update_at, arrival_date,
    color, make, model, price,
    status, vin, year, dealership_id
) 
VALUES
    (1, '2025-12-01', '2025-12-01', '2025-11-01', 'Red', 'Toyota', 'Camry', 800000000.00, 'AVAILABLE', 'VIN000000001', 2023, 1),
    (2, '2026-03-01', '2026-03-01', '2026-02-15', 'Black', 'Honda', 'Civic', 700000000.00, 'AVAILABLE', 'VIN000000002', 2024, 1),
    (3, '2026-04-01', '2026-04-01', '2026-03-20', 'White', 'Mazda', 'CX-5', 900000000.00, 'PENDING', 'VIN000000003', 2025, 1),
    (4, '2025-12-10', '2025-12-10', '2025-11-10', 'Blue', 'BMW', 'X5', 2200000000.00, 'AVAILABLE', 'VIN000000004', 2023, 2),
    (5, '2026-03-05', '2026-03-05', '2026-02-20', 'Gray', 'Audi', 'A4', 1500000000.00, 'AVAILABLE', 'VIN000000005', 2024, 2),
    (6, '2026-04-10', '2026-04-10', '2026-03-25', 'Black', 'Mercedes', 'C200', 1800000000.00, 'SOLD', 'VIN000000006', 2025, 2),
    (7, '2025-11-20', '2025-11-20', '2025-10-20', 'Silver', 'Hyundai', 'SantaFe', 1200000000.00, 'AVAILABLE', 'VIN000000007', 2022, 3),
    (8, '2026-02-15', '2026-02-15', '2026-01-20', 'Red', 'Kia', 'Sorento', 1100000000.00, 'PENDING', 'VIN000000008', 2024, 3),
    (9, '2026-04-20', '2026-04-20', '2026-03-30', 'White', 'Ford', 'Everest', 1300000000.00, 'AVAILABLE', 'VIN000000009', 2025, 3);

CREATE TABLE action_type
INSERT INTO action_type (id, create_at, update_at, description, label)
VALUES
    (1, '2026-05-02', '2026-05-02', NULL, 'Price Reduction Planned'),
    (2, '2026-05-02', '2026-05-02', NULL, 'Promotion Applied'),
    (3, '2026-05-02', '2026-05-02', NULL, 'Other');

```
Create table and insert value:

```sql
CREATE TABLE dealerships
INSERT INTO dealerships (id, create_at, update_at, address, email, name, phone, status) 
VALUES 
    (1, '2026-05-02 21:39:20', '2026-05-02 21:39:20', 'Ho Chi Minh City, VN', 'dealershipT1@gmail.com', 'Dealership T1', '(+84) 911 225 411', 'ACTIVE'), 
    (2, '2026-05-02 21:39:20', '2026-05-02 21:39:20', 'Ho Chi Minh City, VN', 'dealershipT2@gmail.com', 'Dealership T2', '(+84) 911 123 123', 'ACTIVE'), 
    (3, '2026-05-02 21:39:20', '2026-05-02 21:39:20', 'Ho Chi Minh City, VN', 'dealershipT3@gmail.com', 'Dealership T3', '(+84) 911 321 321', 'ACTIVE');

CREATE TABLE vehicles
INSERT INTO vehicles (
    id, create_at, update_at, arrival_date,
    color, make, model, price,
    status, vin, year, dealership_id
) 
VALUES
    (1, '2025-12-01', '2025-12-01', '2025-11-01', 'Red', 'Toyota', 'Camry', 800000000.00, 'AVAILABLE', 'VIN000000001', 2023, 1),
    (2, '2026-03-01', '2026-03-01', '2026-02-15', 'Black', 'Honda', 'Civic', 700000000.00, 'AVAILABLE', 'VIN000000002', 2024, 1),
    (3, '2026-04-01', '2026-04-01', '2026-03-20', 'White', 'Mazda', 'CX-5', 900000000.00, 'PENDING', 'VIN000000003', 2025, 1),
    (4, '2025-12-10', '2025-12-10', '2025-11-10', 'Blue', 'BMW', 'X5', 2200000000.00, 'AVAILABLE', 'VIN000000004', 2023, 2),
    (5, '2026-03-05', '2026-03-05', '2026-02-20', 'Gray', 'Audi', 'A4', 1500000000.00, 'AVAILABLE', 'VIN000000005', 2024, 2),
    (6, '2026-04-10', '2026-04-10', '2026-03-25', 'Black', 'Mercedes', 'C200', 1800000000.00, 'SOLD', 'VIN000000006', 2025, 2),
    (7, '2025-11-20', '2025-11-20', '2025-10-20', 'Silver', 'Hyundai', 'SantaFe', 1200000000.00, 'AVAILABLE', 'VIN000000007', 2022, 3),
    (8, '2026-02-15', '2026-02-15', '2026-01-20', 'Red', 'Kia', 'Sorento', 1100000000.00, 'PENDING', 'VIN000000008', 2024, 3),
    (9, '2026-04-20', '2026-04-20', '2026-03-30', 'White', 'Ford', 'Everest', 1300000000.00, 'AVAILABLE', 'VIN000000009', 2025, 3);

CREATE TABLE action_type
INSERT INTO action_type (id, create_at, update_at, description, label)
VALUES
    (1, '2026-05-02', '2026-05-02', NULL, 'Price Reduction Planned'),
    (2, '2026-05-02', '2026-05-02', NULL, 'Promotion Applied'),
    (3, '2026-05-02', '2026-05-02', NULL, 'Other');
```
or restore database from
```
/the-intelligent-inventory/mysql/init.sql
```
---

## 🚀 4. Run Application

### Option 1: Using Maven

```bash
mvn clean install
mvn spring-boot:run
```

### Option 2: Run JAR file

```bash
mvn clean package
java -jar target/the-intelligent-inventory.jar
```

---

## 🧪 5. Run Unit Tests

```bash
mvn test
```

---

## 📚 6. API Documentation

After starting the application, access Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 💡 Notes

* Make sure MySQL and Redis are running before starting the application
* Docker helps simplify setup and ensures consistency across environments
* When using docker database will have value:
```
Table dealerships
id,name,email,phone,status,address
1,Dealership T1,dealershipT1@gmail.com,(+84) 911 225 411,ACTIVE,"Ho Chi Minh City, VN"
2,Dealership T2,dealershipT2@gmail.com,(+84) 911 123 123,ACTIVE,"Ho Chi Minh City, VN"
3,Dealership T3,dealershipT3@gmail.com,(+84) 911 321 321,ACTIVE,"Ho Chi Minh City, VN"

Table vehicles
id,make,model,year,price (VNĐ),status,vin,dealer_id
1,Toyota,Camry,2023,"800,000,000",AVAILABLE,VIN000000001,1
2,Honda,Civic,2024,"700,000,000",AVAILABLE,VIN000000002,1
3,Mazda,CX-5,2025,"900,000,000",PENDING,VIN000000003,1
4,BMW,X5,2023,"2,200,000,000",AVAILABLE,VIN000000004,2
5,Audi,A4,2024,"1,500,000,000",AVAILABLE,VIN000000005,2
6,Mercedes,C200,2025,"1,800,000,000",SOLD,VIN000000006,2
7,Hyundai,SantaFe,2022,"1,200,000,000",AVAILABLE,VIN000000007,3
8,Kia,Sorento,2024,"1,100,000,000",PENDING,VIN000000008,3
9,Ford,Everest,2025,"1,300,000,000",AVAILABLE,VIN000000009,3

Table action_type
id,label,description
1,Price Reduction Planned,NULL
2,Promotion Applied,NULL
3,Other,NULL
```
