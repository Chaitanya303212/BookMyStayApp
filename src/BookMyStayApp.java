import java.util.*;

/**
 * UC6: Reservation Confirmation & Room Allocation
 * Demonstrates safe allocation, inventory update, and prevention of double booking.
 *
 * @author Chaitanya
 * @version 1.0
 */

// Reservation (Request)
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

// Inventory Service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// Booking Service (Allocation)
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.replace(" ", "").substring(0, 3).toUpperCase()
                + "_" + UUID.randomUUID().toString().substring(0, 5);
    }

    // Process queue
    public void processBookings(Queue<Reservation> queue, InventoryService inventory) {

        System.out.println("=== Processing Reservations ===\n");

        while (!queue.isEmpty()) {

            Reservation request = queue.poll(); // dequeue
            String type = request.getRoomType();

            System.out.println("Processing: " + request.getGuestName());

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(type);

                // Prevent duplicate allocation
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // Decrement inventory immediately
                    inventory.decrement(type);

                    // Confirm booking
                    System.out.println("✅ Confirmed for " + request.getGuestName());
                    System.out.println("Room Type: " + type);
                    System.out.println("Room ID: " + roomId + "\n");

                }

            } else {
                System.out.println("❌ No rooms available for " + type + "\n");
            }
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        InventoryService inventory = new InventoryService();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Suite Room", 0);

        // Booking request queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();

        queue.add(new Reservation("Chaitanya", "Single Room"));
        queue.add(new Reservation("Rahul", "Single Room"));
        queue.add(new Reservation("Ankit", "Single Room")); // exceeds availability
        queue.add(new Reservation("Neha", "Suite Room"));   // no availability

        // Process bookings
        BookingService service = new BookingService();
        service.processBookings(queue, inventory);
    }
}