import java.util.ArrayList;
import java.util.List;

// Represents a reservation made by a guest
class Reservation {
    private int reservationId;
    private String guestName;
    private String roomType;
    private String checkInDate;
    private String checkOutDate;

    public Reservation(int reservationId, String guestName, String roomType, String checkInDate, String checkOutDate) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Check-In: " + checkInDate +
                ", Check-Out: " + checkOutDate;
    }
}

// Stores confirmed reservations in insertion order
class BookingHistory {
    private List<RReservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(RReservation RReservation) {
        history.add(RReservation);
    }

    // Retrieve all reservations
    public List<RReservation> getAllReservations() {
        return history;
    }
}

// Generates reports from booking history
class BookingReportService {
    private BookingHistory1 bookingHistory1;

    public BookingReportService(BookingHistory1 bookingHistory1) {
        this.bookingHistory1 = bookingHistory1;
    }

    // Print all reservations
    public void generateFullReport() {
        System.out.println("==== Full Booking Report ====");
        for (RReservation r : bookingHistory1.getAllReservations()) {
            System.out.println(r);
        }
        System.out.println("Total Reservations: " + bookingHistory1.getAllReservations().size());
        System.out.println("=============================");
    }
}

// Main class simulating Use Case 8
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory1 bookingHistory1 = new BookingHistory1();
        BBookingReportService reportService = new BBookingReportService(bookingHistory1);

        // Simulate confirmed bookings
        RReservation res1 = new RReservation(101, "Alice", "Deluxe", "2026-03-20", "2026-03-22");
        RReservation res2 = new RReservation(102, "Bob", "Standard", "2026-03-21", "2026-03-23");
        RReservation res3 = new RReservation(103, "Charlie", "Suite", "2026-03-22", "2026-03-25");

        // Add reservations to history
        bookingHistory1.addReservation(res1);
        bookingHistory1.addReservation(res2);
        bookingHistory1.addReservation(res3);

        // Admin generates report
        reportService.generateFullReport();
    }
}