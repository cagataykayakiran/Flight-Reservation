package com.flightreservation.service.seat;

import com.flightreservation.dto.request.SeatDetailRequest;
import com.flightreservation.dto.response.SeatResponse;
import com.flightreservation.entity.Seat;
import com.flightreservation.enums.SeatClass;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ISeatService {

    List<SeatResponse> getAllSeatsByFlight(Long flightId);

    SeatResponse addSeat(SeatClass seatClass, BigDecimal price, Long flightId);

    Set<Seat> addSeatsToFlight(Long flightId, List<SeatDetailRequest> seatDetailRequests);

    void deleteSeat(Long seatId, Long flightId);

    SeatResponse updateSeat(Long seatId, BigDecimal price, SeatClass seatClass);

    Seat getSeatById(Long seatId);

    List<Seat> findAndLockSeatsByNumbersAndFlightId(List<String> seatNumbers, Long flightId);

    void save(Seat seat);

}