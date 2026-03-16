import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 *
 * Demonstrates attaching optional services to an existing reservation
 * without modifying core booking or inventory logic.
 *
 * @version 7.1
 */

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("BookMyStay - Add-On Service Selection (v7.1)");
        System.out.println("---------------------------------------------");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example reservation IDs (from confirmed bookings)
        String reservation1 = "RES1001";
        String reservation2 = "RES1002";

        // Guest selects services
        manager.addService(reservation1, new Service("Breakfast", 500));
        manager.addService(reservation1, new Service("Airport Pickup", 1200));
        manager.addService(reservation1, new Service("Spa Access", 1500));

        manager.addService(reservation2, new Service("Breakfast", 500));
        manager.addService(reservation2, new Service("Extra Bed", 800));

        // Display services
        manager.displayServices(reservation1);
        manager.displayServices(reservation2);

        // Display cost
        System.out.println("\nTotal Add-On Cost for " + reservation1 +
                ": ₹" + manager.calculateTotalCost(reservation1));

        System.out.println("Total Add-On Cost for " + reservation2 +
                ": ₹" + manager.calculateTotalCost(reservation2));
    }
}

/**
 * Represents an optional service
 *
 * @version 7.0
 */
class Service {

    private String serviceName;
    private int cost;

    public Service(String serviceName, int cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(serviceName + " (₹" + cost + ")");
    }
}

/**
 * Manages add-on services for reservations
 *
 * reservationID -> List of services
 *
 * @version 7.0
 */
class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Attach service to reservation
    public void addService(String reservationId, Service service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added to " + reservationId +
                ": " + service.getServiceName());
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation " + reservationId + ":");

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            s.displayService();
        }
    }

    // Calculate total add-on cost
    public int calculateTotalCost(String reservationId) {

        int total = 0;

        List<Service> services = reservationServices.get(reservationId);

        if (services != null) {

            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}