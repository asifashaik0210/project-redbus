package com.redbus.user.service;

import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.entity.TicketCost;
import com.redbus.operator.repository.BusOperatorRepository;
import com.redbus.operator.repository.TicketCostRepository;
import com.redbus.user.entity.Booking;
import com.redbus.user.payload.BookingDetailsDto;
import com.redbus.user.payload.PassengerDetails;
import com.redbus.user.repository.BookingRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BookingService {
    @Value("${stripe.secret-key}")
    private String secretKey;


    private BusOperatorRepository busOperatorRepository;
    private TicketCostRepository ticketCostRepository;
    private BookingRepository bookingRepository;

    public BookingService(BusOperatorRepository busOperatorRepository, TicketCostRepository ticketCostRepository, BookingRepository bookingRepository) {
        this.busOperatorRepository = busOperatorRepository;
        this.ticketCostRepository = ticketCostRepository;
        this.bookingRepository = bookingRepository;
    }
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }


    public BookingDetailsDto createBooking(String busId, String ticketId, PassengerDetails passengerDetails) throws StripeException {
        BusOperator bus = busOperatorRepository.findById(busId).get();
        TicketCost ticketCost = ticketCostRepository.findById(ticketId).get();
        Long amount = Math.round(ticketCost.getCost());// Assuming cost is the amount to be paid
        String currency = "usd"; // You may adjust this based on your requirements
        String clientSecret = createPaymentIntent(amount, currency);
        if (clientSecret != null) {

            Booking booking = new Booking();
            String bookingId = UUID.randomUUID().toString();// Generate UUID as the bookingId
            booking.setBookingId(bookingId);
            booking.setBusId(busId);
            booking.setTicketId(ticketId);
            booking.setFrom(bus.getDepartureCity());
            booking.setTo(bus.getArrivalCity());
            booking.setBusCompany(bus.getBusOperatorCompanyName());
            booking.setPrice(ticketCost.getCost());
            booking.setArrivalCity(bus.getArrivalCity());
            booking.setDepartureCity(bus.getDepartureCity());
            booking.setArrivalTime(bus.getArrivalTime());
            booking.setDepartureTime(bus.getDepartureTime());
            booking.setArrivalDate(bus.getArrivalDate());
            booking.setDepartureDate(bus.getDepartureDate());
            booking.setTotalTravelTime(bus.getTotalTravelTime());
            booking.setFirstName(passengerDetails.getFirstName());
            booking.setLastName(passengerDetails.getLastName());
            booking.setEmail(passengerDetails.getEmail());
            booking.setMobile(passengerDetails.getMobile());
            // Set other booking details as needed

            // Save the booking entity
            Booking ticketCreatedDetails = bookingRepository.save(booking);

            BookingDetailsDto dto = new BookingDetailsDto();

            dto.setBookingId(ticketCreatedDetails.getBookingId());// Use the generated UUID
            dto.setFrom(ticketCreatedDetails.getFrom());
            dto.setTo(ticketCreatedDetails.getTo());
            dto.setFirstName(ticketCreatedDetails.getFirstName());
            dto.setLastName(ticketCreatedDetails.getLastName());
            dto.setEmail(ticketCreatedDetails.getEmail());
            dto.setMobile(ticketCreatedDetails.getMobile());
            dto.setBusCompany(ticketCreatedDetails.getBusCompany());
            dto.setArrivalCity(ticketCreatedDetails.getArrivalCity());
            dto.setDepartureCity(ticketCreatedDetails.getDepartureCity());
            dto.setArrivalTime(ticketCreatedDetails.getArrivalTime());
            dto.setDepartureTime(ticketCreatedDetails.getDepartureTime());
            dto.setArrivalDate(ticketCreatedDetails.getArrivalDate());
            dto.setDepartureDate(ticketCreatedDetails.getDepartureDate());
            dto.setTo(ticketCreatedDetails.getArrivalCity());
            dto.setFrom(ticketCreatedDetails.getDepartureCity());
            dto.setTotalTravelTime(ticketCreatedDetails.getTotalTravelTime());
            dto.setPrice(ticketCreatedDetails.getPrice());
            dto.setClientSecret(clientSecret);
            dto.setMessage("Booking Confirmed");

            return dto;

        }
        else{
            System.out.println("Error!!");
        }
        return null;
    }

        public String createPaymentIntent ( Long amount, String currency) throws StripeException {
            // Convert the currency code to lowercase
            String lowercaseCurrency = currency.toLowerCase();

            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount);
            params.put("currency", lowercaseCurrency);

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return paymentIntent.getClientSecret();
        }


    }

