import java.util.HashMap;
import java.util.Map;

/**
 * UC4: Room Search & Availability Check
 * Demonstrates read-only access, filtering, and separation of concerns.
 *
 * @author Chaitanya
 * @version 1.0
 */

// Room class (Domain Model)
class Room {

    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void displayDetails() {
        System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

// Inventory (State Holder)
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}

// Search Service (Read-only)
class RoomSearchService {

    public void searchAvailableRooms(Room[] rooms, RoomInventory inventory) {

        System.out.println("=== Available Rooms ===\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Defensive check: only show available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize rooms
        Room[] rooms = {
                new Room("Single Room", 1, 2000),
                new Room("Double Room", 2, 3500),
                new Room("Suite Room", 3, 6000)
        };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 0); // unavailable
        inventory.addRoomType("Suite Room", 2);

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Perform search (read-only)
        searchService.searchAvailableRooms(rooms, inventory);
    }
}