/**
 * Use Case 8: Booking History & Reporting
 * Book My Stay Application
 *
 * @author Pari
 * @version 8.1
 */

import java.util.*;

// Reservation (confirmed booking)
class Reservation {
  private String reservationId;
  private String guestName;
  private String roomType;

  public Reservation(String reservationId, String guestName, String roomType) {
    this.reservationId = reservationId;
    this.guestName = guestName;
    this.roomType = roomType;
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
}

// Booking History (List maintains order)
class BookingHistory {

  private List<Reservation> history = new ArrayList<>();

  // Add confirmed booking
  public void addReservation(Reservation r) {
    history.add(r);
  }

  // Get all bookings
  public List<Reservation> getAllReservations() {
    return history;
  }
}

// Reporting Service
class BookingReportService {

  // Display all bookings
  public void showAllBookings(List<Reservation> reservations) {

    System.out.println("\nBooking History:\n");

    for (Reservation r : reservations) {
      System.out.println("ID: " + r.getReservationId() +
              " | Guest: " + r.getGuestName() +
              " | Room: " + r.getRoomType());
    }
  }

  // Generate summary report
  public void generateSummary(List<Reservation> reservations) {

    System.out.println("\nBooking Summary Report:\n");

    Map<String, Integer> countMap = new HashMap<>();

    for (Reservation r : reservations) {
      String type = r.getRoomType();
      countMap.put(type, countMap.getOrDefault(type, 0) + 1);
    }

    for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
      System.out.println(entry.getKey() + " booked: " + entry.getValue());
    }
  }
}

// Main class
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v8.1\n");

    BookingHistory history = new BookingHistory();
    BookingReportService report = new BookingReportService();

    // Add confirmed bookings
    history.addReservation(new Reservation("RES101", "Alice", "Single Room"));
    history.addReservation(new Reservation("RES102", "Bob", "Double Room"));
    history.addReservation(new Reservation("RES103", "Charlie", "Single Room"));
    history.addReservation(new Reservation("RES104", "David", "Suite Room"));

    // Show history
    report.showAllBookings(history.getAllReservations());

    // Generate report
    report.generateSummary(history.getAllReservations());
  }
}