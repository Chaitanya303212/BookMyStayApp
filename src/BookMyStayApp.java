import java.io.*;
import java.util.*;

/**
 * UC12: Data Persistence & System Recovery
 * Demonstrates saving and restoring system state using serialization.
 *
 * @author Chaitanya
 * @version 1.0
 */

// Reservation (Serializable)
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// System State (Serializable)
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public void save(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("✅ System state saved successfully.");

        } catch (IOException e) {
            System.out.println("❌ Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public SystemState load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("✅ System state loaded successfully.");
            return (SystemState) in.readObject();

        } catch (Exception e) {
            System.out.println("⚠️ No previous state found. Starting fresh.");
            return null;
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        // Try loading previous state
        SystemState state = persistence.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;
        } else {
            // Initialize fresh system
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            bookings = new ArrayList<>();

            bookings.add(new Reservation("RES101", "Chaitanya", "Single Room"));
            bookings.add(new Reservation("RES102", "Rahul", "Double Room"));
        }

        // Display current state
        System.out.println("\n=== Current Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }

        System.out.println("\n=== Booking History ===");
        for (Reservation r : bookings) {
            r.display();
        }

        // Simulate shutdown → save state
        SystemState newState = new SystemState(inventory, bookings);
        persistence.save(newState);
    }
}