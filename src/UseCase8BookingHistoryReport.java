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
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Generates reports from booking history
class BookingReportService {
    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    // Print all reservations
    public void generateFullReport() {
        System.out.println("==== Full Booking Report ====");
        for (Reservation r : bookingHistory.getAllReservations()) {
            System.out.println(r);
        }
        System.out.println("Total Reservations: " + bookingHistory.getAllReservations().size());
        System.out.println("=============================");
    }
}

// Main class simulating Use Case 8
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService(bookingHistory);

        // Simulate confirmed bookings
        Reservation res1 = new Reservation(101, "Alice", "Deluxe", "2026-03-20", "2026-03-22");
        Reservation res2 = new Reservation(102, "Bob", "Standard", "2026-03-21", "2026-03-23");
        Reservation res3 = new Reservation(103, "Charlie", "Suite", "2026-03-22", "2026-03-25");

        // Add reservations to history
        bookingHistory.addReservation(res1);
        bookingHistory.addReservation(res2);
        bookingHistory.addReservation(res3);

        // Admin generates report
        reportService.generateFullReport();
    }
}