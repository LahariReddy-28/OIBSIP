# 📚 Digital Library Management System

A web-based **Digital Library Management System** developed using **Java, Spring Boot, Thymeleaf, Spring Data JPA, Hibernate, and SQLite**.

The system provides separate functionalities for **Users and Administrators**. Users can register, log in, browse books, borrow books, return books, make reservations, and view fines. Administrators can manage books, users, issued books, fines, reservations, and user queries.

---

## 🎯 Project Objective

The main objective of this project is to develop a simple and efficient digital library platform that allows users to manage their library activities online.

The system reduces manual library operations by providing features such as:

* User registration and login
* Book searching and browsing
* Book borrowing
* Book returning
* Book reservations
* Fine management
* User management
* Book management
* Admin dashboard
* Contact and user query management

---

## 🛠️ Technologies Used

| Technology        | Purpose                           |
| ----------------- | --------------------------------- |
| Java 17           | Backend programming               |
| Spring Boot 3.5.4 | Application framework             |
| Spring MVC        | Web request handling              |
| Spring Data JPA   | Database operations               |
| Hibernate         | ORM                               |
| Thymeleaf         | Frontend template engine          |
| HTML5             | Web page structure                |
| CSS3              | User interface styling            |
| SQLite            | Database                          |
| Maven             | Dependency and project management |
| VS Code           | Development environment           |
| Git & GitHub      | Version control                   |

---

## 👥 User Roles

### 👤 User

Users can:

* Register an account
* Login to the system
* View the user dashboard
* Browse available books
* Search for books
* Borrow books
* Return borrowed books
* Reserve books
* View issued books
* View reservations
* View fines
* Submit queries/contact messages

### 👨‍💼 Administrator

Administrators can:

* Login to the admin dashboard
* Add books
* Edit books
* Delete/manage books
* View registered users
* Manage issued books
* Manage reservations
* Manage fines
* View user queries
* Monitor library activities

---

## ✨ Main Features

### 🔐 Authentication

* User registration
* User login
* Admin login
* Role-based functionality
* Session-based user access

### 📖 Book Management

* Add new books
* View books
* Search books
* Edit book information
* Delete books
* Track book availability

### 📚 Borrowing System

Users can borrow available books.

The system records:

* User
* Book
* Borrow date
* Due date
* Return status

### 🔄 Book Return

Users can return borrowed books.

The system updates the book availability after the return.

### 📌 Reservation System

Users can reserve books when required.

Reservations can be viewed and managed through the system.

### 💰 Fine Management

The system provides fine-related functionality for overdue books.

Users can view their fines, while administrators can manage fine information.

### 👥 User Management

Administrators can view registered users and manage library user information.

### 📩 Contact / Query Management

Users can submit questions or messages.

Administrators can view submitted queries from the admin dashboard.

---

## 🏗️ Project Architecture

The project follows a layered Spring Boot architecture:

```text
DigitalLibraryManagementSystem
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── DigitalLibraryManagementSystem
│   │   │       │
│   │   │       ├── Controller
│   │   │       │   ├── AdminBookController.java
│   │   │       │   ├── AdminController.java
│   │   │       │   ├── AdminFineController.java
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── BookController.java
│   │   │       │   ├── BookingController.java
│   │   │       │   ├── BorrowController.java
│   │   │       │   ├── ContactController.java
│   │   │       │   ├── DashboardController.java
│   │   │       │   ├── FineController.java
│   │   │       │   ├── HomeController.java
│   │   │       │   ├── ReservationController.java
│   │   │       │   └── UserController.java
│   │   │       │
│   │   │       ├── model
│   │   │       │   ├── Book.java
│   │   │       │   ├── Borrow.java
│   │   │       │   ├── ContactMessage.java
│   │   │       │   ├── Reservation.java
│   │   │       │   └── User.java
│   │   │       │
│   │   │       ├── repository
│   │   │       │   ├── BookRepository.java
│   │   │       │   ├── BorrowRepository.java
│   │   │       │   ├── ContactMessageRepository.java
│   │   │       │   ├── ReservationRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       │
│   │   │       └── LibraryApplication.java
│   │   │
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       │   ├── add-book.html
│   │       │   ├── admin-books.html
│   │       │   ├── admin-fines.html
│   │       │   ├── admin-queries.html
│   │       │   ├── admin.html
│   │       │   ├── bookings.html
│   │       │   ├── books.html
│   │       │   ├── contact.html
│   │       │   ├── dashboard.html
│   │       │   ├── edit-book.html
│   │       │   ├── fines.html
│   │       │   ├── home.html
│   │       │   ├── issued-books.html
│   │       │   ├── login.html
│   │       │   ├── my-books.html
│   │       │   ├── register.html
│   │       │   ├── reservations.html
│   │       │   └── users.html
│   │       │
│   │       └── application.properties
│   │
│   └── test
│       └── java
│
├── Screenshots
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## 🔄 Application Flow

```text
                    ┌──────────────────┐
                    │      Home Page   │
                    └────────┬─────────┘
                             │
                 ┌───────────┴───────────┐
                 │                       │
                 ▼                       ▼
          ┌─────────────┐         ┌─────────────┐
          │ User Login  │         │ Admin Login │
          └──────┬──────┘         └──────┬──────┘
                 │                       │
                 ▼                       ▼
          ┌─────────────┐         ┌─────────────┐
          │User Dashboard│         │Admin Dashboard│
          └──────┬──────┘         └──────┬──────┘
                 │                       │
        ┌────────┼────────┐       ┌──────┼──────────┐
        ▼        ▼        ▼       ▼      ▼          ▼
      Books    Borrow   Reserve  Books  Users      Fines
        │        │        │       │      │          │
        └────────┴────────┘       └──────┴──────────┘
                 │                       │
                 ▼                       ▼
          ┌─────────────┐         ┌─────────────┐
          │   SQLite    │◄────────│ Spring Data │
          │   Database  │         │     JPA     │
          └─────────────┘         └─────────────┘
```

---

## 🗄️ Database

The project uses **SQLite** for data storage.

Main entities include:

* `User`
* `Book`
* `Borrow`
* `Reservation`
* `ContactMessage`

The application uses **Spring Data JPA and Hibernate** to communicate with the database.

---

## ▶️ How to Run the Project

### 1. Clone the repository

```bash
git clone https://github.com/LahariReddy-28/OIBSIP.git
```

### 2. Open the project

Open the following folder in VS Code:

```text
Java-Task5-DigitalLibraryManagementSystem
```

### 3. Check Java version

```bash
java -version
```

The project is configured for **Java 17**.

### 4. Build the project

```bash
./mvnw clean
```

Then:

```bash
./mvnw test
```

### 5. Run the application

```bash
./mvnw spring-boot:run
```

If the Maven wrapper is not working, use:

```bash
mvn spring-boot:run
```

### 6. Open the application

After the application starts successfully, open:

```text
http://localhost:8080
```

If port `8080` is already being used, configure another port in:

```text
src/main/resources/application.properties
```

For example:

```properties
server.port=8081
```

Then open:

```text
http://localhost:8081
```

---

## 📸 Screenshots

The `ScreenShots` folder contains screenshots demonstrating the working application.

Recommended screenshots include:

1. Login Page
2. User Dashboard
3. Admin Dashboard
4. Book Search
5. Book Borrowing
6. My Books
7. Reservations
8. Fine Management
9. Book Management
10. User Management

---

## 🧪 Testing

The project uses Maven for testing.

Run:

```bash
./mvnw test
```

A successful test/build result should display:

```text
BUILD SUCCESS
```

---

## 🔧 Important Maven Commands

### Clean

```bash
./mvnw clean
```

### Compile

```bash
./mvnw compile
```

### Test

```bash
./mvnw test
```

### Package

```bash
./mvnw package
```

### Run Spring Boot

```bash
./mvnw spring-boot:run
```

---

## 📌 GitHub Repository Structure

The internship repository follows the required Oasis Infobyte structure:

```text
OIBSIP
│
├── Java-Task1-OnlineReservationSystem
│
└── Java-Task5-DigitalLibraryManagementSystem
    │
    ├── src
    ├── ScreenShots
    ├── README.md
    ├── pom.xml
    ├── mvnw
    ├── mvnw.cmd
    └── application files
``
---

## 📊 Key Learning Outcomes

Through this project, I gained practical experience in:

* Java programming
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* Thymeleaf
* SQLite database integration
* CRUD operations
* MVC architecture
* Web application development
* Git and GitHub
* Maven project management
* User and admin role management

---

## 🚀 Future Enhancements

Possible future improvements include:

* Password encryption
* Email notifications
* Advanced book search and filtering
* Online fine payment
* Book recommendation system
* REST API integration
* Improved authentication and authorization
* Cloud database integration
* Responsive mobile-friendly UI
* Automated testing

---

## 👩‍💻 Author

**Lahari Reddy**

Java Development Intern

---

## 📜 Internship Task

**Track:** Java Development

**Task:** Digital Library Management System

**Repository:** `OIBSIP`

---

## ⭐ Conclusion

The **Digital Library Management System** provides a digital solution for managing common library operations.

By combining **Java, Spring Boot, Thymeleaf, Spring Data JPA, Hibernate, and SQLite**, the project demonstrates how a complete web-based application can be designed using a structured MVC architecture.

The project also provided practical experience in database management, backend development, frontend integration, CRUD operations, version control, and GitHub-based project submission.
