# 🚗 The Intelligent Inventory

## 📌 1. Requirements

* Java 17+
* Maven
* Docker *(optional, recommended)*

---

## 📥 2. Clone Project

```bash
git clone <your-repo-url>
cd the-intelligent-inventory
```

---

## 🛠 3. Run Dependencies (MySQL + Redis)

### Option 1: Use Docker *(Recommended)*

```bash
docker-compose up -d
```

### Option 2: Run Manually

* MySQL: `localhost:3306`
* Redis: `localhost:6379`

Create database:

```sql
CREATE DATABASE intelligent_inventory_dashboard;
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
