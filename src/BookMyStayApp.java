import java.util.*;

/**
 * UC10: Booking Cancellation & Inventory Rollback
 * Demonstrates safe cancellation and state recovery.
 *
 * @author Chaitanya
 * @version 1.0
 */

// Reservation
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        this.active = false;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType +
                " | RoomID: " + roomId + " | Status: " + (active ? "CONFIRMED" : "CANCELLED"));
    }
}

// Inventory Service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public void increment(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    public void displayInventory() {
        System.out.println("\n=== Inventory State ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
    }
}

// Cancellation Service
class CancellationService {

    private Map<String, Reservation> bookingMap;
    private Set<String> rollbackIds = new HashSet<>();

    public CancellationService(Map<String, Reservation> bookingMap) {
        this.bookingMap = bookingMap;
    }

    public void cancelBooking(String reservationId, InventoryService inventory) {

        System.out.println("\nProcessing cancellation for: " + reservationId);

        // Validate booking existence
        if (!bookingMap.containsKey(reservationId)) {
            System.out.println("❌ Reservation not found.");
            return;
        }

        Reservation res = bookingMap.get(reservationId);

        // Check if already cancelled
        if (!res.isActive()) {
            System.out.println("❌ Reservation already cancelled.");
            return;
        }

        // Record rollback room ID
        rollbackIds.add(res.getRoomId());

        // Restore inventory
        inventory.increment(res.getRoomType());

        // Update booking status
        res.cancel();

        System.out.println("✅ Cancellation successful for " + reservationId);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Inventory setup
        InventoryService inventory = new InventoryService();
        inventory.addRoomType("Single Room", 1);
        inventory.addRoomType("Double Room", 0);

        // Booking history (confirmed bookings)
        Map<String, Reservation> bookings = new HashMap<>();

        bookings.put("RES101", new Reservation("RES101", "Chaitanya", "Single Room", "SIN_123"));
        bookings.put("RES102", new Reservation("RES102", "Rahul", "Double Room", "DBL_456"));

        // Display initial bookings
        System.out.println("=== Booking History ===");
        for (Reservation r : bookings.values()) {
            r.display();
        }

        // Cancellation service
        CancellationService service = new CancellationService(bookings);

        // Cancel a valid booking
        service.cancelBooking("RES101", inventory);

        // Try cancelling again
        service.cancelBooking("RES101", inventory);

        // Invalid ID
        service.cancelBooking("RES999", inventory);

        // Display updated bookings
        System.out.println("\n=== Updated Booking History ===");
        for (Reservation r : bookings.values()) {
            r.display();
        }

        // Display updated inventory
        inventory.displayInventory();
    }
}