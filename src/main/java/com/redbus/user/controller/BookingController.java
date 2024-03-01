package com.redbus.user.controller;

import com.redbus.user.payload.BookingDetailsDto;
import com.redbus.user.payload.PassengerDetails;
import com.redbus.user.service.BookingService;
import com.redbus.util.EmailService;
import com.redbus.util.PdfService;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;
    private EmailService emailService;
    private  PdfService pdfService;
    public BookingController(BookingService bookingService,PdfService pdfService,EmailService emailService) {

        this.bookingService = bookingService;
        this.pdfService = pdfService;
        this.emailService = emailService;

    }
            //http://localhost:8080/api/bookings?busId=&ticketId=
            @PostMapping
            public ResponseEntity<?> bookBus(@RequestParam("busId") String busId,
                                             @RequestParam("ticketId") String ticketId,
                                             @RequestBody PassengerDetails passengerDetails) {
                try {
                    BookingDetailsDto booking = bookingService.createBooking(busId, ticketId, passengerDetails);

                    if (booking != null) {
                        // Generate PDF
                        byte[] pdfBytes = pdfService.generatePdf(booking);

                        // Send confirmation email with attachment
                        sendConfirmationEmailWithAttachment(passengerDetails, booking, pdfBytes);

                        return new ResponseEntity<>(booking, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("Booking failed", HttpStatus.NOT_FOUND);
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Log the exception for debugging
                    return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

    private void sendConfirmationEmailWithAttachment(PassengerDetails passengerDetails, BookingDetailsDto booking, byte[] pdfBytes) {
        try {
            emailService.sendEmailWithAttachment(passengerDetails.getEmail(),
                    "Booking Confirmed. Booking Id: " + booking.getBookingId(),
                    "Your Booking is Confirmed \nName: " + passengerDetails.getFirstName() + " " + passengerDetails.getLastName(),
                    pdfBytes, "booking-details.pdf");
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }
}


