/**
 * Use Case 5: Booking Request (First-Come-First-Served)
 * Book My Stay Application
 *
 * @author Pari
 * @version 5.1
 */

import java.util.LinkedList;
import java.util.Queue;

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
}

// Booking Queue class
class BookingQueue {

  private Queue<Reservation> queue;

  public BookingQueue() {
    queue = new LinkedList<>();
  }

  // Add request (enqueue)
  public void addRequest(Reservation reservation) {
    queue.add(reservation);
    System.out.println("Booking request added: "
            + reservation.getGuestName() + " -> " + reservation.getRoomType());
  }

  // Display queue
  public void displayQueue() {
    System.out.println("\nCurrent Booking Queue:\n");

    for (Reservation r : queue) {
      System.out.println(r.getGuestName() + " requested " + r.getRoomType());
    }
  }
}

// Main class
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v5.1\n");

    BookingQueue bookingQueue = new BookingQueue();

    // Add booking requests (FIFO order)
    bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
    bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
    bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

    // Display queue (order preserved)
    bookingQueue.displayQueue();
  }
}