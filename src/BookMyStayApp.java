import java.util.*;

/**
 * UC7: Add-On Service Selection
 * Demonstrates extension of booking with optional services
 * without modifying core booking logic.
 *
 * @author Chaitanya
 * @version 1.0
 */

// Add-On Service (Domain)
class AddOnService {

    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void display() {
        System.out.println(name + " - ₹" + price);
    }
}

// Reservation (Core Booking Reference)
class Reservation {

    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map reservation ID → list of services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getName());
    }

    // Display services + cost
    public void displayServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        double total = 0;

        System.out.println("\n=== Add-On Services ===");

        for (AddOnService s : services) {
            s.display();
            total += s.getPrice();
        }

        System.out.println("Total Add-On Cost: ₹" + total);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Existing reservation (from UC6)
        Reservation reservation = new Reservation("RES123", "Chaitanya");

        // Add-on manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        manager.addService(reservation.getReservationId(), new AddOnService("Breakfast", 500));
        manager.addService(reservation.getReservationId(), new AddOnService("Airport Pickup", 1200));
        manager.addService(reservation.getReservationId(), new AddOnService("Extra Bed", 800));

        // Display selected services
        manager.displayServices(reservation.getReservationId());
    }
}