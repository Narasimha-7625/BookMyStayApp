import java.util.LinkedList;
import java.util.Queue;

/**
 * Use Case 5: Booking Request Queue (First-Come-First-Served)
 * Demonstrates FIFO booking request handling using Queue.
 *
 * @version 5.1
 */

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("BookMyStay - Booking Request Queue (v5.1)");
        System.out.println("------------------------------------------");

        BookingRequestQueue queue = new BookingRequestQueue();

        // Guest booking requests
        RReservation r1 = new RReservation("Rahul", "Single Room");
        RReservation r2 = new RReservation("Priya", "Double Room");
        RReservation r3 = new RReservation("Arjun", "Suite Room");

        // Add requests to queue
        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        // Display queue
        queue.displayRequests();
    }
}

/**
 * Represents a guest reservation request.
 *
 * @version 5.0
 */
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

/**
 * Manages booking requests using FIFO Queue.
 *
 * @version 5.0
 */
class BookingRequestQueue {

    private Queue<RReservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(RReservation RReservation) {
        requestQueue.offer(RReservation);
        System.out.println("Booking request added for " + RReservation.getGuestName());
    }

    // Display queued requests
    public void displayRequests() {

        System.out.println("\nPending Booking Requests:");
        System.out.println("--------------------------");

        for (RReservation r : requestQueue) {
            r.displayReservation();
        }
    }
}