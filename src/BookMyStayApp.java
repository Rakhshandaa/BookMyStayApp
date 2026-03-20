/**
 * Use Case 2: Basic Room Types & Static Availability
 * Book My Stay Application
 *
 * @author Pari
 * @version 2.1
 */

// Abstract class
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

// Single Room
class SingleRoom extends Room {
  public SingleRoom() {
    super("Single Room", 1, 2000);
  }

  public void displayDetails() {
    System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
  }
}

// Double Room
class DoubleRoom extends Room {
  public DoubleRoom() {
    super("Double Room", 2, 3500);
  }

  public void displayDetails() {
    System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
  }
}

// Suite Room
class SuiteRoom extends Room {
  public SuiteRoom() {
    super("Suite Room", 3, 6000);
  }

  public void displayDetails() {
    System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
  }
}

// Main class (COMMON for all use cases)
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v2.1\n");

    // Polymorphism
    Room r1 = new SingleRoom();
    Room r2 = new DoubleRoom();
    Room r3 = new SuiteRoom();

    // Static availability
    int singleAvailable = 5;
    int doubleAvailable = 3;
    int suiteAvailable = 2;

    System.out.println("Room Details & Availability:\n");

    r1.displayDetails();
    System.out.println("Available: " + singleAvailable + "\n");

    r2.displayDetails();
    System.out.println("Available: " + doubleAvailable + "\n");

    r3.displayDetails();
    System.out.println("Available: " + suiteAvailable);
  }
}
