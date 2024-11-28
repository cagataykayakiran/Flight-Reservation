package com.flightreservation.repository;

import com.flightreservation.common.BaseRepository;
import com.flightreservation.entity.Seat;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface SeatRepository extends BaseRepository<Seat> {


    @Query("SELECT MAX(s.seatPosition) FROM Seat s WHERE s.flight.id = :flightId")
    Integer findMaxSeatPositionByFlightId(@Param("flightId") Long flightId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.seatNumber IN :seatNumbers AND s.flight.id = :flightId")
    List<Seat> findAndLockBySeatNumbersAndFlightId(@Param("seatNumbers") List<String> seatNumbers, @Param("flightId") Long flightId);

    List<Seat> findByFlightId(Long flightId);

}
