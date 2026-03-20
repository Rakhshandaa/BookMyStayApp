/**
 * Use Case 3: Centralized Room Inventory Management
 * Book My Stay Application
 *
 * @author Pari
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Inventory class
class RoomInventory {

  private HashMap<String, Integer> inventory;

  public RoomInventory() {
    inventory = new HashMap<>();

    inventory.put("Single Room", 5);
    inventory.put("Double Room", 3);
    inventory.put("Suite Room", 2);
  }

  public int getAvailability(String roomType) {
    return inventory.getOrDefault(roomType, 0);
  }

  public void updateAvailability(String roomType, int count) {
    inventory.put(roomType, count);
  }

  public void displayInventory() {
    System.out.println("Current Room Inventory:\n");

    for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
      System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
    }
  }
}

// Main class (COMMON NAME)
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v3.1\n");

    RoomInventory inventory = new RoomInventory();

    inventory.displayInventory();

    System.out.println("\nUpdating availability...\n");
    inventory.updateAvailability("Single Room", 4);

    inventory.displayInventory();
  }
}