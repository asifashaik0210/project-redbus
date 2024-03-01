package com.redbus.user.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.redbus.operator.util.CustomDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsDto {
    private String bookingId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String arrivalCity;
    private String departureCity;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date arrivalDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @Temporal(TemporalType.DATE)
    private Date departureDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime arrivalTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departureTime;

    @Column(name = "destination", nullable = false)
    private String to;

    @Column(name = "origin", nullable = false)
    private String from;


    private String busCompany;
    private double price;
    private double totalTravelTime;
    private String message;
    private String clientSecret;
}

