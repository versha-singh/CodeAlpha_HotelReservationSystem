/**
 * Represents one hotel room.
 * Each room has a number, a category (Standard / Deluxe / Suite) and a booking status.
 */
public class Room {

    // Room categories with price per night (in Rs.)
    public enum Category {
        STANDARD(2000),
        DELUXE(3500),
        SUITE(6000);

        private final double pricePerNight;

        Category(double pricePerNight) {
            this.pricePerNight = pricePerNight;
        }

        public double getPricePerNight() {
            return pricePerNight;
        }
    }

    private int roomNumber;
    private Category category;
    private boolean booked;

    public Room(int roomNumber, Category category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.booked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}
