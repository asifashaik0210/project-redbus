package com.redbus.util;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.redbus.user.payload.BookingDetailsDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

@Service
public class PdfService {

    public byte[] generatePdf(BookingDetailsDto bookingDetails) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            // Add content to the PDF
            addContentToPdf(document, bookingDetails);

            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addContentToPdf(Document document, BookingDetailsDto bookingDetails) throws DocumentException {
        // Add details to the PDF
        document.add(new Paragraph("Booking Details"));
        document.add(new Paragraph("Booking ID: " + bookingDetails.getBookingId()));
        document.add(new Paragraph("First Name: " + bookingDetails.getFirstName()));
        document.add(new Paragraph("Last Name: " + bookingDetails.getLastName()));
        document.add(new Paragraph("Email: " + bookingDetails.getEmail()));
        document.add(new Paragraph("Mobile: " + bookingDetails.getMobile()));
        document.add(new Paragraph("Arrival City: " + bookingDetails.getArrivalCity()));
        document.add(new Paragraph("Departure City: " + bookingDetails.getDepartureCity()));
        document.add(new Paragraph("Arrival Date: " + bookingDetails.getArrivalDate()));
        document.add(new Paragraph("Departure Date: " + bookingDetails.getDepartureDate()));
        document.add(new Paragraph("Arrival Time: " + bookingDetails.getArrivalTime()));
        document.add(new Paragraph("Departure Time: " + bookingDetails.getDepartureTime()));
        document.add(new Paragraph("Destination: " + bookingDetails.getTo()));
        document.add(new Paragraph("Origin: " + bookingDetails.getFrom()));
        document.add(new Paragraph("Bus Company: " + bookingDetails.getBusCompany()));
        document.add(new Paragraph("Price: " + bookingDetails.getPrice()));
        document.add(new Paragraph("Total Travel Time: " + bookingDetails.getTotalTravelTime()));
    }

    // If you want to save the PDF to a file, you can use this method
    public void savePdfToFile(byte[] pdfBytes, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

