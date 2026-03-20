import java.util.LinkedList;
import java.util.Queue;

/**
 * UC5: Booking Request (First-Come-First-Served)
 * Demonstrates Queue data structure and FIFO principle.
 *
 * @author Chaitanya
 * @version 1.0
 */

// Reservation class (represents booking request)
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

    public void display() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

// Booking Queue (FIFO)
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    // Add request
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // Display queue
    public void displayQueue() {

        System.out.println("\n=== Booking Request Queue ===");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize queue
        BookingQueue bookingQueue = new BookingQueue();

        // Simulate booking requests
        bookingQueue.addRequest(new Reservation("Chaitanya", "Single Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Double Room"));
        bookingQueue.addRequest(new Reservation("Ankit", "Suite Room"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();

        System.out.println("\nRequests are stored in FIFO order (First-Come-First-Served).");
    }
}