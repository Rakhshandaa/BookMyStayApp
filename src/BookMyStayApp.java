/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Book My Stay Application
 *
 * @author Pari
 * @version 10.1
 */

import java.util.*;

// Reservation
class Reservation {
  private String reservationId;
  private String roomType;
  private String roomId;

  public Reservation(String reservationId, String roomType, String roomId) {
    this.reservationId = reservationId;
    this.roomType = roomType;
    this.roomId = roomId;
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
}

// Inventory
class RoomInventory {
  private Map<String, Integer> inventory = new HashMap<>();

  public RoomInventory() {
    inventory.put("Single Room", 1);
    inventory.put("Double Room", 1);
  }

  public void increaseAvailability(String type) {
    inventory.put(type, inventory.getOrDefault(type, 0) + 1);
  }

  public void displayInventory() {
    System.out.println("\nCurrent Inventory:");
    for (Map.Entry<String, Integer> e : inventory.entrySet()) {
      System.out.println(e.getKey() + " -> " + e.getValue());
    }
  }
}

// Booking History
class BookingHistory {
  private Map<String, Reservation> bookings = new HashMap<>();

  public void addReservation(Reservation r) {
    bookings.put(r.getReservationId(), r);
  }

  public Reservation getReservation(String id) {
    return bookings.get(id);
  }

  public void removeReservation(String id) {
    bookings.remove(id);
  }
}

// Cancellation Service
class CancellationService {

  private Stack<String> rollbackStack = new Stack<>();

  public void cancelBooking(String reservationId,
                            BookingHistory history,
                            RoomInventory inventory) {

    Reservation r = history.getReservation(reservationId);

    // Validation
    if (r == null) {
      System.out.println("Cancellation FAILED: Reservation not found");
      return;
    }

    // Push to stack (LIFO tracking)
    rollbackStack.push(r.getRoomId());

    // Restore inventory
    inventory.increaseAvailability(r.getRoomType());

    // Remove booking
    history.removeReservation(reservationId);

    System.out.println("Cancellation SUCCESS for Reservation ID: " + reservationId);
    System.out.println("Released Room ID: " + r.getRoomId());
  }

  // Show rollback stack
  public void showRollbackStack() {
    System.out.println("\nRollback Stack (Recent Releases): " + rollbackStack);
  }
}

// Main class
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v10.1\n");

    RoomInventory inventory = new RoomInventory();
    BookingHistory history = new BookingHistory();
    CancellationService service = new CancellationService();

    // Existing confirmed bookings
    history.addReservation(new Reservation("RES101", "Single Room", "SI01"));
    history.addReservation(new Reservation("RES102", "Double Room", "DO01"));

    // Cancel valid booking
    service.cancelBooking("RES101", history, inventory);

    // Try invalid cancellation
    service.cancelBooking("RES999", history, inventory);

    // Display inventory after rollback
    inventory.displayInventory();

    // Show rollback stack
    service.showRollbackStack();
  }
}