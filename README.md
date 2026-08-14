# 📚 Digital Library Management System

A web-based **Digital Library Management System** developed using Java Spring Boot, Thymeleaf, Spring Data JPA, Hibernate, and SQLite.

The system allows users to browse, borrow, return, and monitor books while administrators can manage books, users, issued books, fines, and user queries.

---

## 🚀 Features

### 👤 User Features

* User login
* User dashboard
* Browse books
* View available quantity
* Borrow books
* View borrowed books
* Automatic due-date calculation
* Return books
* View return status
* View fines
* Submit queries/contact messages

### 🛡️ Admin Features

* Admin dashboard
* Manage books
* Manage users
* View issued books
* Monitor fines
* View user queries
* Resolve queries
* Delete queries

---

## 🛠️ Technology Stack

* **Java 17**
* **Spring Boot 3.5.4**
* **Spring MVC**
* **Spring Data JPA**
* **Hibernate**
* **Thymeleaf**
* **SQLite**
* **HTML5**
* **CSS3**
* **Maven**
* **VS Code**

---

## 📁 Project Structure

```text
DigitalLibraryManagementSystem
│
├── src
│   └── main
│       ├── java
│       │   └── DigitalLibraryManagementSystem
│       │       │
│       │       ├── Controller
│       │       │   ├── LoginController.java
│       │       │   ├── BookController.java
│       │       │   ├── BorrowController.java
│       │       │   ├── FineController.java
│       │       │   ├── AdminController.java
│       │       │   ├── AdminFineController.java
│       │       │   └── ContactController.java
│       │       │
│       │       ├── model
│       │       │   ├── User.java
│       │       │   ├── Book.java
│       │       │   ├── Borrow.java
│       │       │   └── ContactMessage.java
│       │       │
│       │       ├── repository
│       │       │   ├── UserRepository.java
│       │       │   ├── BookRepository.java
│       │       │   ├── BorrowRepository.java
│       │       │   └── ContactMessageRepository.java
│       │       │
│       │       └── LibraryApplication.java
│       │
│       └── resources
│           ├── templates
│           │   ├── login.html
│           │   ├── dashboard.html
│           │   ├── books.html
│           │   ├── my-books.html
│           │   ├── fines.html
│           │   ├── contact.html
│           │   ├── admin-dashboard.html
│           │   ├── issued-books.html
│           │   ├── admin-fines.html
│           │   └── admin-queries.html
│           │
│           └── application.properties
│
├── pom.xml
└── README.md
```

---

## 🗄️ Database

The application uses **SQLite**.

The main database entities are:

```text
User
Book
Borrow
ContactMessage
```

### Borrow Relationship

```text
User 1 ──────── * Borrow * ──────── 1 Book
```

One user can have multiple borrowing records.

One book can appear in multiple borrowing records over time.

---

## 📅 Borrowing Rules

The default borrowing period is:

```text
14 days
```

The due date is automatically calculated:

```text
Due Date = Borrow Date + 14 Days
```

---

## 💰 Fine Rules

Fine per late day:

```text
₹10
```

Formula:

```text
Fine = Late Days × ₹10
```

---

## ▶️ How to Run

### Step 1 — Open the project

Open the project folder in VS Code.

### Step 2 — Verify Java

Run:

```bash
java -version
```

The project uses Java 17.

### Step 3 — Verify Maven

Run:

```bash
mvn -version
```

### Step 4 — Build the project

```bash
mvn clean install
```

### Step 5 — Run the application

```bash
mvn spring-boot:run
```

### Step 6 — Open the application

Open:

```text
http://localhost:8081
```

If your configured port is different, use the port specified in `application.properties`.

---

## ⚙️ Configuration

Example:

```properties
server.port=8081

spring.datasource.url=jdbc:sqlite:library.db
spring.datasource.driver-class-name=org.sqlite.JDBC

spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update

spring.thymeleaf.cache=false
```

Use the exact database configuration already present in your project if it differs.

---

## 🔄 Application Flow

```text
Login
  ↓
Dashboard
  ↓
Browse Books
  ↓
Borrow Book
  ↓
My Books
  ↓
Due Date
  ↓
Return Book
  ↓
Book Availability Updated
```

For administrators:

```text
Admin Login
     ↓
Admin Dashboard
     ↓
Manage Books
     ↓
Issued Books
     ↓
Users
     ↓
Fines
     ↓
User Queries
```

---

## 🧪 Testing

Important test cases include:

* Valid login
* Invalid login
* Book availability
* Book borrowing
* Duplicate borrowing prevention
* Due-date calculation
* Book return
* Quantity update
* Fine calculation
* Query submission
* Query resolution
* Query deletion
* Admin issued-book monitoring

---

## 🔮 Future Enhancements

* Spring Security
* Password encryption
* Online payment
* Email notifications
* Advanced search
* Book reservations
* Reports
* Dashboard charts
* REST APIs
* Cloud deployment
* Mobile application

---

## 👩‍💻 Project

**Project:** Digital Library Management System

**Backend:** Java + Spring Boot

**Frontend:** Thymeleaf + HTML + CSS

**Database:** SQLite

**ORM:** Hibernate / JPA

**Build Tool:** Maven

---

## 📌 Conclusion

The Digital Library Management System provides an efficient platform for managing books, users, borrowing, returns, fines, issued books, and user queries through a simple web interface.
