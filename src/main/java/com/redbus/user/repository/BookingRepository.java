package com.redbus.user.repository;

import com.redbus.user.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,String> {

}
