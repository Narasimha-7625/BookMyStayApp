import java.util.ArrayList;
import java.util.List;

// Custom exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Represents a reservation
class RReservation {
    private int reservationId;
    private String guestName;
    private String roomType;
    private String checkInDate;
    private String checkOutDate;

    public RReservation(int reservationId, String guestName, String roomType, String checkInDate, String checkOutDate) throws InvalidBookingException {
        // Validate input during creation
        if (!isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }
        if (!isValidDate(checkInDate) || !isValidDate(checkOutDate)) {
            throw new InvalidBookingException("Invalid date format. Use YYYY-MM-DD");
        }

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Simple room type validation
    private boolean isValidRoomType(String roomType) {
        return roomType.equals("Standard") || roomType.equals("Deluxe") || roomType.equals("Suite");
    }

    // Simple date format check (YYYY-MM-DD)
    private boolean isValidDate(String date) {
        return date != null && date.matches("\\d{4}-\\d{2}-\\d{2}");
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

// Stores booking history
class BookingHistory1 {
    private List<RReservation> history;

    public BookingHistory1() {
        history = new ArrayList<>();
    }

    public void addReservation(RReservation RReservation) {
        history.add(RReservation);
    }

    public List<RReservation> getAllReservations() {
        return history;
    }
}

// Generates reports
class BBookingReportService {
    private BookingHistory1 bookingHistory1;

    public BBookingReportService(BookingHistory1 bookingHistory1) {
        this.bookingHistory1 = bookingHistory1;
    }

    public void generateFullReport() {
        System.out.println("==== Full Booking Report ====");
        if (bookingHistory1.getAllReservations().isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (RReservation r : bookingHistory1.getAllReservations()) {
                System.out.println(r);
            }
            System.out.println("Total Reservations: " + bookingHistory1.getAllReservations().size());
        }
        System.out.println("=============================");
    }
}

// Main program
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        BookingHistory1 bookingHistory1 = new BookingHistory1();
        BBookingReportService reportService = new BBookingReportService(bookingHistory1);

        // Array of test bookings, some invalid
        Object[][] testBookings = {
                {101, "Alice", "Deluxe", "2026-03-20", "2026-03-22"},       // valid
                {102, "Bob", "Economy", "2026-03-21", "2026-03-23"},        // invalid room type
                {103, "", "Suite", "2026-03-22", "2026-03-25"},             // invalid guest name
                {104, "Charlie", "Standard", "2026-03-22", "03-25-2026"}    // invalid date format
        };

        for (Object[] bookingData : testBookings) {
            try {
                RReservation RReservation = new RReservation(
                        (int) bookingData[0],
                        (String) bookingData[1],
                        (String) bookingData[2],
                        (String) bookingData[3],
                        (String) bookingData[4]
                );
                bookingHistory1.addReservation(RReservation);
                System.out.println("Booking confirmed: " + RReservation);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        }

        // Generate report
        reportService.generateFullReport();
    }
}