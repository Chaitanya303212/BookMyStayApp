import java.util.*;

/**
 * UC8: Booking History & Reporting
 * Demonstrates maintaining booking history and generating reports.
 *
 * @author Chaitanya
 * @version 1.0
 */

// Reservation (Confirmed Booking)
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType + " | ₹" + price);
    }
}

// Booking History (Insertion Order Maintained)
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Report Service
class BookingReportService {

    // Display all bookings
    public void displayAll(List<Reservation> reservations) {

        System.out.println("=== Booking History ===\n");

        for (Reservation r : reservations) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\n=== Booking Summary Report ===");

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : reservations) {

            totalRevenue += r.getPrice();

            roomCount.put(r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);

        System.out.println("\nRoom Type Distribution:");
        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("RES101", "Chaitanya", "Single Room", 2000));
        history.addReservation(new Reservation("RES102", "Rahul", "Double Room", 3500));
        history.addReservation(new Reservation("RES103", "Neha", "Single Room", 2000));
        history.addReservation(new Reservation("RES104", "Ankit", "Suite Room", 6000));

        // Report service
        BookingReportService reportService = new BookingReportService();

        // Display all bookings
        reportService.displayAll(history.getAllReservations());

        // Generate report
        reportService.generateSummary(history.getAllReservations());
    }
}