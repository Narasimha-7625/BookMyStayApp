import java.util.*;

/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates FIFO request processing and safe room allocation
 * while preventing double booking.
 *
 * @version 6.1
 */

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("BookMyStay - Room Allocation Service (v6.1)");
        System.out.println("--------------------------------------------");

        // Initialize services
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        // Guests submit booking requests
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Double Room"));
        queue.addRequest(new Reservation("Arjun", "Suite Room"));
        queue.addRequest(new Reservation("Meena", "Single Room"));

        System.out.println("\nProcessing Booking Requests...\n");

        // Process queue
        while (!queue.isEmpty()) {

            Reservation reservation = queue.getNextRequest();
            allocationService.allocateRoom(reservation);
        }

        System.out.println("\nFinal Allocations:");
        allocationService.displayAllocations();
    }
}

/**
 * Reservation request
 * @version 6.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * Booking request queue using FIFO
 * @version 6.0
 */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/**
 * Central inventory
 * @version 6.0
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {

        int count = inventory.get(roomType);
        inventory.put(roomType, count - 1);
    }
}

/**
 * Room allocation service
 * Ensures unique room IDs
 *
 * @version 6.0
 */
class RoomAllocationService {

    private RoomInventory inventory;

    // RoomType -> Assigned Room IDs
    private Map<String, Set<String>> allocatedRooms;

    // Global room ID tracker
    private Set<String> usedRoomIds;

    public RoomAllocationService(RoomInventory inventory) {

        this.inventory = inventory;

        allocatedRooms = new HashMap<>();
        usedRoomIds = new HashSet<>();
    }

    public void allocateRoom(Reservation reservation) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {

            System.out.println("Booking failed for " +
                    reservation.getGuestName() +
                    " (No " + roomType + " available)");

            return;
        }

        String roomId = generateRoomId(roomType);

        usedRoomIds.add(roomId);

        allocatedRooms
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        inventory.decrement(roomType);

        System.out.println("Reservation confirmed for " +
                reservation.getGuestName() +
                " | Room ID: " + roomId);
    }

    private String generateRoomId(String roomType) {

        String prefix;

        if (roomType.equals("Single Room"))
            prefix = "S";
        else if (roomType.equals("Double Room"))
            prefix = "D";
        else
            prefix = "SU";

        String roomId;

        do {
            roomId = prefix + (100 + usedRoomIds.size());
        }
        while (usedRoomIds.contains(roomId));

        return roomId;
    }

    public void displayAllocations() {

        for (String type : allocatedRooms.keySet()) {

            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }
}