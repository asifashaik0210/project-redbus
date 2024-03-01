package com.redbus.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.redbus.operator.util.CustomDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="bookings")
public class Booking {

    @Id
    @Column(name="booking_id")
   private String bookingId;

 @Column(name = "bus_id")
 private String busId;
 @Column(name = "ticket_id")
 private String ticketId;

    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    private String email;
    private String mobile;
    @Column(name="arrival_city")
    private String arrivalCity;
    @Column(name="departure_city")
    private String departureCity;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @Temporal(TemporalType.DATE)
    @Column(name="arrival_date")
    private Date arrivalDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @Temporal(TemporalType.DATE)
    @Column(name="departure_date")
    private Date departureDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name="arrival_time")
    private LocalTime arrivalTime;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name="departure_time")
    private LocalTime departureTime;

 @Column(name = "destination", nullable = false)
 private String to;

 @Column(name = "origin", nullable = false)
 private String from;


 @Column(name="bus_company")
    private String busCompany;

    private double price;
    @Column(name="total_travel_time")
    private double totalTravelTime;
}
