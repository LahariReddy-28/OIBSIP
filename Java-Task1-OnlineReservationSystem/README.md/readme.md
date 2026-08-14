# Online Reservation System

## Project Description

The Online Reservation System is a GUI-based train reservation application
developed using Java Swing, JDBC, and SQLite.

The system allows users to log in, book train tickets, generate a unique
PNR number, view booking information, and cancel tickets using the PNR number.

## Objectives

- Provide secure user login
- Allow users to select train details
- Book train tickets
- Generate unique PNR numbers
- Store reservation information in SQLite
- Allow users to retrieve bookings using PNR
- Allow users to cancel reservations

## Technologies Used

- Java
- Java Swing
- JDBC
- SQLite
- DB Browser for SQLite
- Visual Studio Code

## Features

### Login

Users can log in using a valid username and password.
Invalid credentials are rejected.

### Reservation

Users can enter:

- Passenger name
- Train number
- Train name
- Class type
- Journey date
- Source station
- Destination station

### PNR Generation

After successful booking, the system generates a unique PNR number.

### Cancellation

Users can enter their PNR number to retrieve booking details and cancel
the reservation.

## Database

SQLite is used as the database.

Tables:

- users
- trains
- reservations

## Project Structure

```text
src/
├── Main.java
├── dao/
│   ├── LoginDAO.java
│   ├── ReservationDAO.java
│   └── TrainDAO.java
├── database/
│   └── DBConnection.java
├── model/
│   └── Reservation.java
└── ui/
    ├── LoginFrame.java
    ├── ReservationFrame.java
    └── CancellationFrame.java