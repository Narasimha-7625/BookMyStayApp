import java.util.*;

class Booking {
    String bookingId;
    String roomId;
    String guestName;
    boolean isCancelled;

    public Booking(String bookingId, String roomId, String guestName) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.guestName = guestName;
        this.isCancelled = false;
    }
}

public class UseCase10BookingCancellation {

    // Inventory count for room types
    static Map<String, Integer> roomInventory = new HashMap<>();
    // Active bookings
    static Map<String, Booking> bookings = new HashMap<>();
    // Stack to track recently released room IDs (LIFO)
    static Stack<String> rollbackStack = new Stack<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialize inventory
        roomInventory.put("Single", 5);
        roomInventory.put("Double", 3);

        // Simulate some bookings
        bookings.put("B001", new Booking("B001", "R101", "Alice"));
        bookings.put("B002", new Booking("B002", "R102", "Bob"));

        System.out.println("Welcome to Booking Cancellation System");

        while (true) {
            System.out.println("\n1. View Bookings");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Inventory");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewBookings();
                    break;
                case 2:
                    System.out.print("Enter Booking ID to cancel: ");
                    String bookingId = sc.nextLine();
                    cancelBooking(bookingId);
                    break;
                case 3:
                    viewInventory();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        System.out.println("Current Bookings:");
        for (Booking b : bookings.values()) {
            System.out.println("ID: " + b.bookingId + ", Room: " + b.roomId + ", Guest: " + b.guestName +
                    ", Cancelled: " + b.isCancelled);
        }
    }

    static void viewInventory() {
        System.out.println("Room Inventory:");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + ", Available: " + entry.getValue());
        }
    }

    static void cancelBooking(String bookingId) {
        if (!bookings.containsKey(bookingId)) {
            System.out.println("Booking ID does not exist.");
            return;
        }

        Booking booking = bookings.get(bookingId);

        if (booking.isCancelled) {
            System.out.println("Booking is already cancelled.");
            return;
        }

        // Perform rollback: release room ID
        rollbackStack.push(booking.roomId);

        // Restore inventory count (assuming room type can be inferred from room ID, simplified here)
        String roomType = booking.roomId.startsWith("R1") ? "Single" : "Double";
        roomInventory.put(roomType, roomInventory.getOrDefault(roomType, 0) + 1);

        // Update booking history
        booking.isCancelled = true;

        System.out.println("Booking " + bookingId + " cancelled successfully.");
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }
}