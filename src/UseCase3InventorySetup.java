import java.util.HashMap;
import java.util.Map;

/**
 * Manages centralized room inventory using HashMap.
 *
 * @version 3.0
 */
 class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes room availability
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update room availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display full inventory
    public void displayInventory() {

        System.out.println("Current Room Inventory:");
        System.out.println("-----------------------");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Available: " + entry.getValue());
        }
    }
}

/**
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates how HashMap can manage room availability
 * as a centralized inventory system.
 *
 * @version 3.1
 */
public class UseCase3InventorySetup{

    public static void main(String[] args) {

        System.out.println("BookMyStay - Inventory Management (v3.1)");
        System.out.println("----------------------------------------");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating inventory for Double Room...\n");

        inventory.updateAvailability("Double Room", 4);

        // Display updated inventory
        inventory.displayInventory();

    }
}