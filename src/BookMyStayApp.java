/**
 * Use Case 7: Add-On Service Selection
 * Book My Stay Application
 *
 * @author Pari
 * @version 7.1
 */

import java.util.*;

// Add-On Service class
class Service {
  private String serviceName;
  private double cost;

  public Service(String serviceName, double cost) {
    this.serviceName = serviceName;
    this.cost = cost;
  }

  public String getServiceName() {
    return serviceName;
  }

  public double getCost() {
    return cost;
  }
}

// Add-On Service Manager
class AddOnServiceManager {

  // Map: Reservation ID -> List of Services
  private Map<String, List<Service>> serviceMap = new HashMap<>();

  // Add service to reservation
  public void addService(String reservationId, Service service) {
    serviceMap.putIfAbsent(reservationId, new ArrayList<>());
    serviceMap.get(reservationId).add(service);

    System.out.println("Added service: " + service.getServiceName() +
            " to Reservation: " + reservationId);
  }

  // Display services
  public void displayServices(String reservationId) {

    System.out.println("\nServices for Reservation ID: " + reservationId);

    List<Service> services = serviceMap.get(reservationId);

    if (services == null || services.isEmpty()) {
      System.out.println("No services selected.");
      return;
    }

    double totalCost = 0;

    for (Service s : services) {
      System.out.println("- " + s.getServiceName() + " (₹" + s.getCost() + ")");
      totalCost += s.getCost();
    }

    System.out.println("Total Add-On Cost: ₹" + totalCost);
  }
}

// Main class
public class BookMyStayApp {

  public static void main(String[] args) {

    System.out.println("Welcome to Book My Stay App");
    System.out.println("Hotel Booking System v7.1\n");

    AddOnServiceManager manager = new AddOnServiceManager();

    // Example reservation IDs
    String res1 = "RES101";
    String res2 = "RES102";

    // Add services
    manager.addService(res1, new Service("Breakfast", 500));
    manager.addService(res1, new Service("Airport Pickup", 1200));
    manager.addService(res2, new Service("Extra Bed", 800));

    // Display services
    manager.displayServices(res1);
    manager.displayServices(res2);
  }
}