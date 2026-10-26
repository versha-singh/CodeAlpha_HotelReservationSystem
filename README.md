# Hotel Reservation System

A console-based Java application to search, book and manage hotel rooms. 

## Features

- View all rooms with their category, price and status (Available / Booked)
- Search available rooms by category (Standard, Deluxe, Suite)
- Book a room with automatic total price calculation
- Payment simulation (Card, UPI or Cash at hotel). No real payment is made.
- Cancel a reservation with a simulated refund
- View booking details by booking ID
- View a list of all bookings
- Bookings are saved to a file, so they are still there when the program is restarted
- Input validation for invalid numbers, card numbers and UPI IDs

## Room Categories

| Category | Rooms | Price per night |
|----------|-------|-----------------|
| Standard | 101, 102, 103 | Rs. 2000 |
| Deluxe   | 201, 202, 203 | Rs. 3500 |
| Suite    | 301, 302      | Rs. 6000 |

## Concepts Used

- Object-Oriented Programming (`Room`, `Booking`, `Hotel` classes, `enum` for room categories)
- `ArrayList` to store rooms and bookings
- File I/O (`BufferedReader`, `FileReader`, `PrintWriter`, `FileWriter`) to save and load bookings
- Exception handling (`NumberFormatException`, `IOException`)
- Loops, `switch` statement and methods

## Project Structure

```
HotelReservationSystem/
├── Room.java                    (room number, category, status)
├── Booking.java                 (one reservation and its details)
├── Hotel.java                   (manages rooms, bookings and file saving)
├── HotelReservationSystem.java  (main program with the menu)
└── README.md
```

A file named `bookings.txt` is created automatically when the first booking is made.

## How to Run

1. Make sure Java (JDK 8 or newer) is installed.
2. Download or clone this repository.
3. Open a terminal in the project folder and run:

```
javac Room.java Booking.java Hotel.java HotelReservationSystem.java
java HotelReservationSystem
```

You can also open the project in IntelliJ IDEA and run the `main` method in `HotelReservationSystem`.

## Sample Output

```
===== HOTEL RESERVATION SYSTEM =====
1. View all rooms
2. Search available rooms
3. Book a room
4. Cancel a reservation
5. View booking details
6. View all bookings
7. Exit

Payment successful! Your room is booked.
------------ BOOKING DETAILS ------------
Booking ID     : 1001
Customer name  : Asha
Room number    : 201
Room category  : DELUXE
Nights         : 2
Total amount   : Rs. 7000.00
Payment method : UPI
-----------------------------------------
```

## Author

Versha Singh
