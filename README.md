## 🏨 Hotel Reservation Management System

This is a **Java-based console application** that allows users to manage hotel room reservations with real-time MySQL database integration. It provides a simple text-based interface for performing various CRUD operations using **JDBC**.

## 💡 Features

* 📌 **Reserve Room** – Add a new reservation with guest name, room number, and contact.
* 📋 **View Reservations** – Display all reservation records in a tabular format.
* 🔍 **Get Room Number** – Retrieve a room number using guest name and reservation ID.
* ✏️ **Update Reservation** – Modify guest details or room information.
* ❌ **Delete Reservation** – Remove a reservation by ID.
* 🧠 **User Input Handling** – Dynamic input via `Scanner` for smooth interaction.
* 🔐 **Robust Error Handling** – Exceptions like `SQLException` and `ClassNotFoundException` are gracefully handled.

## 🧰 Technologies Used

* **Java** – Core logic and user interface.
* **JDBC (Java Database Connectivity)** – For executing SQL queries and managing DB connections.
* **MySQL** – Backend relational database to store reservation records.

## 📦 Project Structure

* `Main.java` – Contains the main logic, user menu, and function calls.
* Functions:
  * `reserveRoom()` – Inserts new reservation.
  * `viewReservation()` – Displays all reservations.
  * `getRoomNumber()` – Retrieves room number by guest name and ID.
  * `updateReservation()` – Updates guest/room details.
  * `deleteReservation()` – Deletes reservation by ID.
  * `exit()` – Graceful program termination with countdown.

## 🏁 Getting Started
1. Setup MySQL database and create a table `room_reservation`.
2. Update DB connection URL, user, and password in the code.
3. Compile and run `Main.java`.
4. Use the console to interact with the system.
