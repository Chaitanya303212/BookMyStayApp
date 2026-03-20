import java.util.HashMap;
import java.util.Map;

/**
 * UC3: Centralized Room Inventory Management
 * Demonstrates use of HashMap for managing room availability.
 *
 * @author Chaitanya
 * @version 1.0
 */

// Inventory Class
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    // Register room type
    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    // Get availability
    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    // Update availability
    public void updateAvailability(String type, int newCount) {
        if (inventory.containsKey(type)) {
            inventory.put(type, newCount);
        } else {
            System.out.println("Room type not found.");
        }
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("=== Current Room Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Register room types
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Display inventory
        inventory.displayInventory();

        // Update availability
        System.out.println("\nUpdating availability...\n");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();
    }
}