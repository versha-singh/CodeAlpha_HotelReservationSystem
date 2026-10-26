import java.util.ArrayList;
import java.util.Scanner;

/**
 * Console-based Hotel Reservation System.
 * Features: view and search rooms, book a room, cancel a reservation,
 * payment simulation and booking details view.
 */
public class HotelReservationSystem {

    private static Hotel hotel = new Hotel();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Grand Palace Hotel");
        int choice;
        do {
            showMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    printRooms(hotel.getRooms(), true);
                    break;
                case 2:
                    searchRooms();
                    break;
                case 3:
                    bookRoom();
                    break;
                case 4:
                    cancelReservation();
                    break;
                case 5:
                    viewBookingDetails();
                    break;
                case 6:
                    viewAllBookings();
                    break;
                case 7:
                    System.out.println("Thank you for visiting Grand Palace Hotel. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-7.");
            }
        } while (choice != 7);
    }

    private static void showMenu() {
        System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
        System.out.println("1. View all rooms");
        System.out.println("2. Search available rooms");
        System.out.println("3. Book a room");
        System.out.println("4. Cancel a reservation");
        System.out.println("5. View booking details");
        System.out.println("6. View all bookings");
        System.out.println("7. Exit");
    }

    // Prints a table of rooms
    private static void printRooms(ArrayList<Room> rooms, boolean showStatus) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }
        System.out.println();
        if (showStatus) {
            System.out.printf("%-8s %-12s %-18s %-10s%n", "Room", "Category", "Price per night", "Status");
        } else {
            System.out.printf("%-8s %-12s %-18s%n", "Room", "Category", "Price per night");
        }
        System.out.println("------------------------------------------------");
        for (Room r : rooms) {
            String price = "Rs. " + (int) r.getCategory().getPricePerNight();
            if (showStatus) {
                String status = r.isBooked() ? "Booked" : "Available";
                System.out.printf("%-8d %-12s %-18s %-10s%n", r.getRoomNumber(), r.getCategory(), price, status);
            } else {
                System.out.printf("%-8d %-12s %-18s%n", r.getRoomNumber(), r.getCategory(), price);
            }
        }
    }

    // Asks the user to choose a category. Returns null if the user wants all categories.
    private static Room.Category chooseCategory() {
        System.out.println("Select room category:");
        System.out.println("1. Standard (Rs. 2000 per night)");
        System.out.println("2. Deluxe   (Rs. 3500 per night)");
        System.out.println("3. Suite    (Rs. 6000 per night)");
        System.out.println("4. All categories");
        int c = readIntInRange("Enter choice (1-4): ", 1, 4);
        switch (c) {
            case 1:
                return Room.Category.STANDARD;
            case 2:
                return Room.Category.DELUXE;
            case 3:
                return Room.Category.SUITE;
            default:
                return null;
        }
    }

    // Search available rooms by category
    private static void searchRooms() {
        Room.Category category = chooseCategory();
        ArrayList<Room> available = hotel.searchAvailable(category);
        if (available.isEmpty()) {
            System.out.println("Sorry, no rooms are available for this category.");
        } else {
            printRooms(available, false);
        }
    }

    // Book a room: choose room, enter nights, pay, confirm
    private static void bookRoom() {
        String name = readNonEmpty("Enter customer name: ").replace("|", " ");

        Room.Category category = chooseCategory();
        ArrayList<Room> available = hotel.searchAvailable(category);
        if (available.isEmpty()) {
            System.out.println("Sorry, no rooms are available for this category.");
            return;
        }
        printRooms(available, false);

        int roomNumber = readInt("\nEnter room number to book: ");
        Room room = hotel.findRoom(roomNumber);
        if (room == null || room.isBooked()) {
            System.out.println("Invalid room number or the room is already booked.");
            return;
        }

        int nights = readIntInRange("Enter number of nights (1-30): ", 1, 30);
        double total = room.getCategory().getPricePerNight() * nights;
        System.out.printf("Total amount to pay: Rs. %.2f%n", total);

        String method = simulatePayment(total);
        if (method == null) {
            System.out.println("Payment cancelled. Booking was not made.");
            return;
        }

        Booking booking = hotel.bookRoom(name, room, nights, total, method);
        System.out.println("\nPayment successful! Your room is booked.");
        booking.printDetails();
    }

    // Payment simulation (no real payment happens). Returns the method, or null if cancelled.
    private static String simulatePayment(double total) {
        System.out.println("\nSelect payment method:");
        System.out.println("1. Card");
        System.out.println("2. UPI");
        System.out.println("3. Cash at hotel");
        int m = readIntInRange("Enter choice (1-3): ", 1, 3);

        String method;
        if (m == 1) {
            method = "Card";
            while (true) {
                System.out.print("Enter 16-digit card number: ");
                String card = sc.nextLine().replace(" ", "");
                if (card.matches("\\d{16}")) {
                    break;
                }
                System.out.println("Invalid card number. Please enter exactly 16 digits.");
            }
        } else if (m == 2) {
            method = "UPI";
            while (true) {
                System.out.print("Enter UPI ID (example: name@bank): ");
                String upi = sc.nextLine().trim();
                if (upi.contains("@") && upi.length() > 3) {
                    break;
                }
                System.out.println("Invalid UPI ID.");
            }
        } else {
            method = "Cash at hotel";
        }

        System.out.printf("Confirm payment of Rs. %.2f? (yes/no): ", total);
        String answer = sc.nextLine().trim();
        if (!answer.equalsIgnoreCase("yes")) {
            return null;
        }
        return method;
    }

    // Cancel an existing reservation using the booking ID
    private static void cancelReservation() {
        int id = readInt("Enter booking ID to cancel: ");
        Booking booking = hotel.findBooking(id);
        if (booking == null) {
            System.out.println("No booking found with this ID.");
            return;
        }
        booking.printDetails();
        System.out.print("Are you sure you want to cancel this booking? (yes/no): ");
        String answer = sc.nextLine().trim();
        if (!answer.equalsIgnoreCase("yes")) {
            System.out.println("Cancellation stopped. Your booking is safe.");
            return;
        }
        double refund = booking.getTotalAmount();
        hotel.cancelBooking(id);
        System.out.println("Booking cancelled successfully.");
        System.out.printf("Refund of Rs. %.2f will be processed (simulation).%n", refund);
    }

    // Show details of one booking
    private static void viewBookingDetails() {
        int id = readInt("Enter booking ID: ");
        Booking booking = hotel.findBooking(id);
        if (booking == null) {
            System.out.println("No booking found with this ID.");
        } else {
            booking.printDetails();
        }
    }

    // Show a short list of all bookings
    private static void viewAllBookings() {
        ArrayList<Booking> list = hotel.getBookings();
        if (list.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }
        System.out.println();
        System.out.printf("%-10s %-18s %-6s %-10s %-7s %-12s%n",
                "Booking", "Customer", "Room", "Category", "Nights", "Total (Rs.)");
        System.out.println("-----------------------------------------------------------------");
        for (Booking b : list) {
            System.out.printf("%-10d %-18s %-6d %-10s %-7d %-12.2f%n",
                    b.getBookingId(), b.getCustomerName(), b.getRoomNumber(),
                    b.getCategory(), b.getNights(), b.getTotalAmount());
        }
    }

    // Keep asking until the user types something that is not empty
    private static String readNonEmpty(String message) {
        while (true) {
            System.out.print(message);
            String text = sc.nextLine().trim();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    // Keep asking until the user types a valid whole number
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Keep asking until the number is between min and max
    private static int readIntInRange(String message, int min, int max) {
        while (true) {
            int value = readInt(message);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }
}
