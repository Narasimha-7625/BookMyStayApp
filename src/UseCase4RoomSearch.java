/**
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only search of available rooms without
 * modifying inventory state.
 *
 * @version 4.0
 */

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("BookMyStay - Room Search (v4.0)");
        System.out.println("--------------------------------");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("\nAvailable Rooms:\n");

        // Search logic (READ ONLY)

        if (inventory.getAvailability("Single Room") > 0) {
            single.displayRoomDetails();
            System.out.println("Available: " + inventory.getAvailability("Single Room"));
            System.out.println();
        }

        if (inventory.getAvailability("Double Room") > 0) {
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + inventory.getAvailability("Double Room"));
            System.out.println();
        }

        if (inventory.getAvailability("Suite Room") > 0) {
            suite.displayRoomDetails();
            System.out.println("Available: " + inventory.getAvailability("Suite Room"));
            System.out.println();
        }

        System.out.println("Search completed successfully.");
    }
}