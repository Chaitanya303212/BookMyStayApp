import java.util.HashMap;
import java.util.Map;

/**
 * UC9: Error Handling & Validation
 * Demonstrates validation and safe error handling for booking input.
 *
 * @author Chaitanya
 * @version 1.0
 */

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Validator Class
class BookingValidator {

    public static void validate(String guestName, String roomType, Map<String, Integer> inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("Selected room is not available.");
        }
    }
}

// Booking Service
class BookingService {

    private Map<String, Integer> inventory = new HashMap<>();

    public BookingService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void processBooking(String guestName, String roomType) {

        try {
            // Validate input
            BookingValidator.validate(guestName, roomType, inventory);

            // If valid → confirm booking
            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("✅ Booking confirmed for " + guestName);
            System.out.println("Room Type: " + roomType + "\n");

        } catch (InvalidBookingException e) {
            // Handle error safely
            System.out.println("❌ Booking failed: " + e.getMessage() + "\n");
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingService service = new BookingService();

        // Valid booking
        service.processBooking("Chaitanya", "Single Room");

        // Invalid: empty name
        service.processBooking("", "Double Room");

        // Invalid: wrong room type
        service.processBooking("Rahul", "Deluxe Room");

        // Invalid: no availability
        service.processBooking("Neha", "Suite Room");
    }
}