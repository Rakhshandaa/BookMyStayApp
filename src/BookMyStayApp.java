/**
 * Use Case 12: Data Persistence & System Recovery
 * Book My Stay Application
 *
 * @author Pari
 * @version 12.1
 */

import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
  private String id;
  private String guestName;
  private String roomType;

  public Reservation(String id, String guestName, String roomType) {
    this.id = id;
    this.guestName = guestName;
    this.roomType = roomType;
  }

  public String toString() {
    return id + " | " + guestName + " | " + roomType;
  }
}

// Inventory (Serializable)
class RoomInventory implements Serializable {
  private Map<String, Integer> inventory = new HashMap<>();

  public RoomInventory() {
    inventory.put("Single Room", 2);
    inventory.put("Double Room", 1);
  }

  public Map<String, Integer> getInventory() {
    return inventory;
  }
}

// Persistence Service
class PersistenceService {

  private static final String FILE_NAME = "hotel_data.ser";

  // Save data
  public void save(RoomInventory inventory, List<Reservation> bookings) {

    try (ObjectOutputStream oos =
                 new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

      oos.writeObject(inventory);
      oos.writeObject(bookings);

      System.out.println("Data saved successfully!");

    } catch (IOException e) {
      System.out.println("Error saving data: " + e.getMessage());
    }
  }

  // Load data
  public Object[] load() {

    try (ObjectInputStream ois =
                 new ObjectInputStream(new FileInputStream(FILE_NAME))) {

      RoomInventory inventory = (RoomInventory) ois.readObject();
      List<Reservation> bookings = (List<Reservation>) ois.readObject();

      System.out.println("Data loaded successfully!");
      return new Object[]{inventory, bookings};

    } catch (Exception e) {
      System.out.println("No previous data found. Starting fresh.");
      return null;
    }
  }
}

// Main class
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v12.1\n");

    PersistenceService ps = new PersistenceService();

    RoomInventory inventory;
    List<Reservation> bookings;

    // Load previous state
    Object[] data = ps.load();

    if (data != null) {
      inventory = (RoomInventory) data[0];
      bookings = (List<Reservation>) data[1];
    } else {
      inventory = new RoomInventory();
      bookings = new ArrayList<>();
    }

    // Add new booking
    bookings.add(new Reservation("RES101", "Alice", "Single Room"));

    // Display current state
    System.out.println("\nCurrent Bookings:");
    for (Reservation r : bookings) {
      System.out.println(r);
    }

    System.out.println("\nInventory:");
    for (Map.Entry<String, Integer> e : inventory.getInventory().entrySet()) {
      System.out.println(e.getKey() + " -> " + e.getValue());
    }

    // Save state before exit
    ps.save(inventory, bookings);
  }
}