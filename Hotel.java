import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Manages all rooms and bookings.
 * Bookings are saved in the file "bookings.txt" (File I/O) so they are
 * still available the next time the program is started.
 */
public class Hotel {
    private static final String FILE_NAME = "bookings.txt";

    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();
    private int nextBookingId = 1001;

    public Hotel() {
        createRooms();
        loadBookings();
    }

    // The hotel has 8 rooms: 3 Standard, 3 Deluxe, 2 Suite
    private void createRooms() {
        rooms.add(new Room(101, Room.Category.STANDARD));
        rooms.add(new Room(102, Room.Category.STANDARD));
        rooms.add(new Room(103, Room.Category.STANDARD));
        rooms.add(new Room(201, Room.Category.DELUXE));
        rooms.add(new Room(202, Room.Category.DELUXE));
        rooms.add(new Room(203, Room.Category.DELUXE));
        rooms.add(new Room(301, Room.Category.SUITE));
        rooms.add(new Room(302, Room.Category.SUITE));
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    // Returns available rooms. If category is null, returns available rooms of all categories.
    public ArrayList<Room> searchAvailable(Room.Category category) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room r : rooms) {
            if (!r.isBooked() && (category == null || r.getCategory() == category)) {
                result.add(r);
            }
        }
        return result;
    }

    public Room findRoom(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                return r;
            }
        }
        return null;
    }

    public Booking findBooking(int bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId() == bookingId) {
                return b;
            }
        }
        return null;
    }

    // Creates a booking, marks the room as booked and saves to file
    public Booking bookRoom(String customerName, Room room, int nights, double total, String paymentMethod) {
        Booking booking = new Booking(nextBookingId, customerName, room.getRoomNumber(),
                room.getCategory(), nights, total, paymentMethod);
        nextBookingId++;
        bookings.add(booking);
        room.setBooked(true);
        saveBookings();
        return booking;
    }

    // Cancels a booking, frees the room and saves to file
    public boolean cancelBooking(int bookingId) {
        Booking booking = findBooking(bookingId);
        if (booking == null) {
            return false;
        }
        Room room = findRoom(booking.getRoomNumber());
        if (room != null) {
            room.setBooked(false);
        }
        bookings.remove(booking);
        saveBookings();
        return true;
    }

    // File I/O: write all bookings to bookings.txt
    private void saveBookings() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Booking b : bookings) {
                pw.println(b.getBookingId() + "|" + b.getCustomerName() + "|" + b.getRoomNumber()
                        + "|" + b.getNights() + "|" + b.getTotalAmount() + "|" + b.getPaymentMethod());
            }
        } catch (IOException e) {
            System.out.println("Could not save bookings: " + e.getMessage());
        }
    }

    // File I/O: read bookings from bookings.txt when the program starts
    private void loadBookings() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] p = line.split("\\|");
                try {
                    int id = Integer.parseInt(p[0]);
                    String name = p[1];
                    int roomNo = Integer.parseInt(p[2]);
                    int nights = Integer.parseInt(p[3]);
                    double total = Double.parseDouble(p[4]);
                    String method = p[5];

                    Room room = findRoom(roomNo);
                    if (room != null) {
                        room.setBooked(true);
                        bookings.add(new Booking(id, name, roomNo, room.getCategory(), nights, total, method));
                        if (id >= nextBookingId) {
                            nextBookingId = id + 1;
                        }
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Skipped a damaged line in " + FILE_NAME);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + FILE_NAME + ": " + e.getMessage());
        }
    }
}
