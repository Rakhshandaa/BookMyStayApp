/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Book My Stay Application
 *
 * @author Pari
 * @version 6.1
 */

import java.util.*;

// Reservation (request)
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

// Booking Queue (FIFO)
class BookingQueue {
  private Queue<Reservation> queue = new LinkedList<>();

  public void addRequest(Reservation r) {
    queue.add(r);
  }

  public Reservation getNextRequest() {
    return queue.poll(); // dequeue
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }
}

// Inventory Service
class RoomInventory {
  private HashMap<String, Integer> inventory = new HashMap<>();

  public RoomInventory() {
    inventory.put("Single Room", 2);
    inventory.put("Double Room", 1);
    inventory.put("Suite Room", 1);
  }

  public int getAvailability(String type) {
    return inventory.getOrDefault(type, 0);
  }

  public void reduceAvailability(String type) {
    inventory.put(type, getAvailability(type) - 1);
  }
}

// Booking Service (CORE LOGIC)
class BookingService {

  // Track allocated room IDs (no duplicates)
  private Set<String> allocatedRooms = new HashSet<>();

  // Map room type -> allocated IDs
  private Map<String, Set<String>> roomAllocations = new HashMap<>();

  // Process queue
  public void processBookings(BookingQueue queue, RoomInventory inventory) {

    while (!queue.isEmpty()) {

      Reservation r = queue.getNextRequest();
      String type = r.getRoomType();

      System.out.println("\nProcessing: " + r.getGuestName());

      // Check availability
      if (inventory.getAvailability(type) > 0) {

        // Generate unique room ID
        String roomId = generateRoomId(type);

        // Ensure uniqueness
        while (allocatedRooms.contains(roomId)) {
          roomId = generateRoomId(type);
        }

        // Add to set
        allocatedRooms.add(roomId);

        // Map allocation
        roomAllocations.putIfAbsent(type, new HashSet<>());
        roomAllocations.get(type).add(roomId);

        // Update inventory
        inventory.reduceAvailability(type);

        // Confirm booking
        System.out.println("Booking CONFIRMED for " + r.getGuestName());
        System.out.println("Room Type: " + type + " | Room ID: " + roomId);

      } else {
        System.out.println("Booking FAILED for " + r.getGuestName() + " (No availability)");
      }
    }
  }

  // Generate room ID
  private String generateRoomId(String type) {
    return type.substring(0, 2).toUpperCase() + (int)(Math.random() * 100);
  }
}

// Main class
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v6.1\n");

    // Setup
    BookingQueue queue = new BookingQueue();
    RoomInventory inventory = new RoomInventory();
    BookingService service = new BookingService();

    // Add requests
    queue.addRequest(new Reservation("Alice", "Single Room"));
    queue.addRequest(new Reservation("Bob", "Single Room"));
    queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
    queue.addRequest(new Reservation("David", "Suite Room"));

    // Process bookings
    service.processBookings(queue, inventory);
  }
}