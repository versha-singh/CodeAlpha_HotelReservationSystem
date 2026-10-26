/**
 * Represents one reservation made by a customer.
 */
public class Booking {
    private int bookingId;
    private String customerName;
    private int roomNumber;
    private Room.Category category;
    private int nights;
    private double totalAmount;
    private String paymentMethod;

    public Booking(int bookingId, String customerName, int roomNumber, Room.Category category,
                   int nights, double totalAmount, String paymentMethod) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Room.Category getCategory() {
        return category;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Prints the full booking details
    public void printDetails() {
        System.out.println("------------ BOOKING DETAILS ------------");
        System.out.println("Booking ID     : " + bookingId);
        System.out.println("Customer name  : " + customerName);
        System.out.println("Room number    : " + roomNumber);
        System.out.println("Room category  : " + category);
        System.out.println("Nights         : " + nights);
        System.out.printf("Total amount   : Rs. %.2f%n", totalAmount);
        System.out.println("Payment method : " + paymentMethod);
        System.out.println("-----------------------------------------");
    }
}
