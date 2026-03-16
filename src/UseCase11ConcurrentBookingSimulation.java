import java.util.*;
import java.util.concurrent.*;

// Booking request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared booking system
class BookingSystem {
    private Map<String, Integer> inventory = new HashMap<>();
    private List<String> allocatedRooms = new ArrayList<>();

    public BookingSystem() {
        // Initialize inventory
        inventory.put("Single", 5);
        inventory.put("Double", 3);
    }

    // Unsynchronized booking (can cause race conditions)
    public boolean bookRoomUnsafe(BookingRequest request) {
        int available = inventory.getOrDefault(request.roomType, 0);
        if (available > 0) {
            // Simulate some processing delay
            try { Thread.sleep(50); } catch (InterruptedException e) {}
            inventory.put(request.roomType, available - 1);
            allocatedRooms.add(request.guestName + "-" + request.roomType);
            return true;
        }
        return false;
    }

    // Thread-safe booking using synchronized
    public synchronized boolean bookRoomSafe(BookingRequest request) {
        int available = inventory.getOrDefault(request.roomType, 0);
        if (available > 0) {
            try { Thread.sleep(50); } catch (InterruptedException e) {}
            inventory.put(request.roomType, available - 1);
            allocatedRooms.add(request.guestName + "-" + request.roomType);
            return true;
        }
        return false;
    }

    public void printStatus() {
        System.out.println("\nCurrent Inventory: " + inventory);
        System.out.println("Allocated Rooms: " + allocatedRooms + "\n");
    }
}

// Guest thread
class GuestThread extends Thread {
    private BookingSystem system;
    private BookingRequest request;
    private boolean safeMode;

    public GuestThread(BookingSystem system, BookingRequest request, boolean safeMode) {
        this.system = system;
        this.request = request;
        this.safeMode = safeMode;
    }

    @Override
    public void run() {
        boolean success;
        if (safeMode) {
            success = system.bookRoomSafe(request);
        } else {
            success = system.bookRoomUnsafe(request);
        }

        System.out.println(request.guestName + " booking " + request.roomType +
                (success ? " SUCCESS" : " FAILED"));
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {
        BookingSystem system = new BookingSystem();

        // Simulate multiple guest requests
        BookingRequest[] requests = {
                new BookingRequest("Alice", "Single"),
                new BookingRequest("Bob", "Single"),
                new BookingRequest("Charlie", "Double"),
                new BookingRequest("Daisy", "Single"),
                new BookingRequest("Ethan", "Double"),
                new BookingRequest("Fiona", "Single")
        };

        System.out.println("=== Simulating UNSAFE concurrent booking (race conditions) ===");
        List<Thread> threads = new ArrayList<>();
        for (BookingRequest req : requests) {
            GuestThread t = new GuestThread(system, req, false);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) t.join();
        system.printStatus();

        System.out.println("=== Resetting system for SAFE concurrent booking ===");
        system = new BookingSystem();
        threads.clear();

        for (BookingRequest req : requests) {
            GuestThread t = new GuestThread(system, req, true);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) t.join();
        system.printStatus();
    }
}