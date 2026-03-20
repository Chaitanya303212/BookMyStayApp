import java.util.*;

/**
 * UC11: Concurrent Booking Simulation
 * Demonstrates thread safety using synchronized blocks.
 *
 * @author Chaitanya
 * @version 1.0
 */

// Reservation Request
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

// Inventory (Shared Resource)
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    // Critical section
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\n=== Final Inventory ===");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

// Booking Queue (Shared Queue)
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(BookingQueue queue, InventoryService inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation request;

            // synchronized retrieval
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) break;

            boolean success = inventory.allocateRoom(request.getRoomType());

            if (success) {
                System.out.println("✅ " + request.getGuestName() +
                        " booked " + request.getRoomType());
            } else {
                System.out.println("❌ " + request.getGuestName() +
                        " failed (No availability)");
            }
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) throws InterruptedException {

        InventoryService inventory = new InventoryService();
        BookingQueue queue = new BookingQueue();

        // Simulate multiple guests (concurrent requests)
        queue.addRequest(new Reservation("Chaitanya", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Ankit", "Single Room")); // exceeds
        queue.addRequest(new Reservation("Neha", "Double Room"));
        queue.addRequest(new Reservation("Aman", "Double Room"));  // exceeds

        // Create threads
        Thread t1 = new BookingProcessor(queue, inventory);
        Thread t2 = new BookingProcessor(queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        t1.join();
        t2.join();

        // Final inventory
        inventory.displayInventory();
    }
}