/**
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 * Book My Stay Application
 *
 * @author Pari
 * @version 11.1
 */

import java.util.*;

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

// Shared Booking Queue
class BookingQueue {
  private Queue<Reservation> queue = new LinkedList<>();

  // synchronized to avoid race condition
  public synchronized void addRequest(Reservation r) {
    queue.add(r);
    System.out.println("Request added: " + r.getGuestName());
  }

  public synchronized Reservation getRequest() {
    return queue.poll();
  }

  public synchronized boolean isEmpty() {
    return queue.isEmpty();
  }
}

// Shared Inventory (critical section)
class RoomInventory {
  private Map<String, Integer> inventory = new HashMap<>();

  public RoomInventory() {
    inventory.put("Single Room", 2);
  }

  // synchronized allocation (critical section)
  public synchronized boolean allocateRoom(String type) {

    int available = inventory.getOrDefault(type, 0);

    if (available > 0) {
      inventory.put(type, available - 1);
      return true;
    }
    return false;
  }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

  private BookingQueue queue;
  private RoomInventory inventory;

  public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
    this.queue = queue;
    this.inventory = inventory;
  }

  public void run() {

    while (true) {

      Reservation r;

      synchronized (queue) {
        if (queue.isEmpty()) break;
        r = queue.getRequest();
      }

      if (r != null) {

        boolean success = inventory.allocateRoom(r.getRoomType());

        if (success) {
          System.out.println(Thread.currentThread().getName() +
                  " CONFIRMED for " + r.getGuestName());
        } else {
          System.out.println(Thread.currentThread().getName() +
                  " FAILED for " + r.getGuestName());
        }
      }
    }
  }
}

// Main class
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v11.1\n");

    BookingQueue queue = new BookingQueue();
    RoomInventory inventory = new RoomInventory();

    // Simulate multiple requests
    queue.addRequest(new Reservation("Alice", "Single Room"));
    queue.addRequest(new Reservation("Bob", "Single Room"));
    queue.addRequest(new Reservation("Charlie", "Single Room"));

    // Multiple threads (concurrent users)
    BookingProcessor t1 = new BookingProcessor(queue, inventory);
    BookingProcessor t2 = new BookingProcessor(queue, inventory);

    t1.setName("Thread-1");
    t2.setName("Thread-2");

    t1.start();
    t2.start();
  }
}