/**
 * Use Case 9: Error Handling & Validation
 * Book My Stay Application
 *
 * @author Pari
 * @version 9.1
 */

import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
  public InvalidBookingException(String message) {
    super(message);
  }
}

// Reservation
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

// Inventory
class RoomInventory {
  private Map<String, Integer> inventory = new HashMap<>();

  public RoomInventory() {
    inventory.put("Single Room", 1);
    inventory.put("Double Room", 1);
  }

  public int getAvailability(String type) {
    return inventory.getOrDefault(type, -1);
  }

  public void reduceAvailability(String type) throws InvalidBookingException {
    int available = getAvailability(type);

    if (available <= 0) {
      throw new InvalidBookingException("No rooms available for " + type);
    }

    inventory.put(type, available - 1);
  }

  public boolean isValidRoomType(String type) {
    return inventory.containsKey(type);
  }
}

// Validator (Fail-Fast)
class BookingValidator {

  public void validate(Reservation r, RoomInventory inventory) throws InvalidBookingException {

    if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
      throw new InvalidBookingException("Guest name cannot be empty");
    }

    if (!inventory.isValidRoomType(r.getRoomType())) {
      throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
    }

    if (inventory.getAvailability(r.getRoomType()) <= 0) {
      throw new InvalidBookingException("Room not available: " + r.getRoomType());
    }
  }
}

// Booking Service
class BookingService {

  private BookingValidator validator = new BookingValidator();

  public void processBooking(Reservation r, RoomInventory inventory) {

    try {
      // Validate first (Fail-Fast)
      validator.validate(r, inventory);

      // Allocate (safe state)
      inventory.reduceAvailability(r.getRoomType());

      System.out.println("Booking SUCCESS for " + r.getGuestName() +
              " (" + r.getRoomType() + ")");

    } catch (InvalidBookingException e) {
      // Graceful failure
      System.out.println("Booking FAILED: " + e.getMessage());
    }
  }
}

// Main class
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v9.1\n");

    RoomInventory inventory = new RoomInventory();
    BookingService service = new BookingService();

    // Test cases
    service.processBooking(new Reservation("Alice", "Single Room"), inventory); // success
    service.processBooking(new Reservation("Bob", "Single Room"), inventory);   // fail (no availability)
    service.processBooking(new Reservation("", "Double Room"), inventory);      // fail (empty name)
    service.processBooking(new Reservation("Charlie", "Suite Room"), inventory); // fail (invalid type)
  }
}