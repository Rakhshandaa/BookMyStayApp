/**
 * Use Case 4: Room Search & Availability Check
 * Book My Stay Application
 *
 * @author Pari
 * @version 4.1
 */

import java.util.HashMap;
import java.util.Map;

// Abstract Room class
abstract class Room {
  protected String roomType;
  protected int beds;
  protected double price;

  public Room(String roomType, int beds, double price) {
    this.roomType = roomType;
    this.beds = beds;
    this.price = price;
  }

  public abstract void displayDetails();
}

// Room types
class SingleRoom extends Room {
  public SingleRoom() {
    super("Single Room", 1, 2000);
  }

  public void displayDetails() {
    System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
  }
}

class DoubleRoom extends Room {
  public DoubleRoom() {
    super("Double Room", 2, 3500);
  }

  public void displayDetails() {
    System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
  }
}

class SuiteRoom extends Room {
  public SuiteRoom() {
    super("Suite Room", 3, 6000);
  }

  public void displayDetails() {
    System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
  }
}

// Inventory class
class RoomInventory {
  private HashMap<String, Integer> inventory;

  public RoomInventory() {
    inventory = new HashMap<>();
    inventory.put("Single Room", 5);
    inventory.put("Double Room", 0);
    inventory.put("Suite Room", 2);
  }

  public int getAvailability(String roomType) {
    return inventory.getOrDefault(roomType, 0);
  }

  public Map<String, Integer> getAllInventory() {
    return inventory;
  }
}

// Search Service
class SearchService {

  public void searchAvailableRooms(RoomInventory inventory) {

    System.out.println("Available Rooms:\n");

    for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {

      String type = entry.getKey();
      int available = entry.getValue();

      if (available > 0) {

        Room room = null;

        if (type.equals("Single Room")) {
          room = new SingleRoom();
        } else if (type.equals("Double Room")) {
          room = new DoubleRoom();
        } else if (type.equals("Suite Room")) {
          room = new SuiteRoom();
        }

        if (room != null) {
          room.displayDetails();
          System.out.println("Available: " + available + "\n");
        }
      }
    }
  }
}

// Main class (YOUR REQUIRED NAME)
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v4.1\n");

    RoomInventory inventory = new RoomInventory();
    SearchService search = new SearchService();

    search.searchAvailableRooms(inventory);
  }
}