import java.io.*;
import java.util.*;

// Booking class must implement Serializable for persistence
class Booking1 implements Serializable {
    private static final long serialVersionUID = 1L;
    String bookingId;
    String roomId;
    String guestName;
    boolean isCancelled;

    public Booking1(String bookingId, String roomId, String guestName) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.guestName = guestName;
        this.isCancelled = false;
    }

    @Override
    public String toString() {
        return "ID: " + bookingId + ", Room: " + roomId + ", Guest: " + guestName + ", Cancelled: " + isCancelled;
    }
}

// System state container
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<String, Booking1> bookings = new HashMap<>();
    Map<String, Integer> roomInventory = new HashMap<>();
}

public class UseCase12DataPersistenceRecovery {

    private static final String DATA_FILE = "system_state.dat";
    private SystemState state;

    public UseCase12DataPersistenceRecovery() {
        state = loadState(); // Load persisted state on startup
        if (state == null) {
            state = new SystemState();
            initializeInventory();
        }
    }

    private void initializeInventory() {
        state.roomInventory.put("Single", 5);
        state.roomInventory.put("Double", 3);
    }

    // Save state to file
    private void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    // Load state from file
    private SystemState loadState() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No previous system state found. Starting fresh.");
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("System state restored from file.");
            return (SystemState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading system state. Starting fresh. Details: " + e.getMessage());
            return null;
        }
    }

    // Display bookings
    private void viewBookings() {
        if (state.bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        System.out.println("Current Bookings:");
        state.bookings.values().forEach(System.out::println);
    }

    // Display inventory
    private void viewInventory() {
        System.out.println("Room Inventory:");
        state.roomInventory.forEach((type, count) -> System.out.println("Room Type: " + type + ", Available: " + count));
    }

    // Create a booking
    private void createBooking(String bookingId, String guestName, String roomId) {
        if (state.bookings.containsKey(bookingId)) {
            System.out.println("Booking ID already exists.");
            return;
        }
        String roomType = roomId.startsWith("R1") ? "Single" : "Double";
        int available = state.roomInventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            System.out.println("No rooms available for type: " + roomType);
            return;
        }

        state.bookings.put(bookingId, new Booking1(bookingId, roomId, guestName));
        state.roomInventory.put(roomType, available - 1);
        System.out.println("Booking created successfully.");
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. View Bookings");
            System.out.println("2. View Inventory");
            System.out.println("3. Create Booking");
            System.out.println("4. Save & Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewBookings();
                    break;
                case 2:
                    viewInventory();
                    break;
                case 3:
                    System.out.print("Enter Booking ID: ");
                    String bookingId = sc.nextLine();
                    System.out.print("Enter Guest Name: ");
                    String guestName = sc.nextLine();
                    System.out.print("Enter Room ID (e.g., R101, R201): ");
                    String roomId = sc.nextLine();
                    createBooking(bookingId, guestName, roomId);
                    break;
                case 4:
                    saveState();
                    System.out.println("Exiting system...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        UseCase12DataPersistenceRecovery system = new UseCase12DataPersistenceRecovery();
        system.run();
    }
}