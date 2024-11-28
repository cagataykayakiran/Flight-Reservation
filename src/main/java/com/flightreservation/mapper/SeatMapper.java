package com.flightreservation.mapper;

import com.flightreservation.dto.response.SeatResponse;
import com.flightreservation.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SeatMapper {

    public SeatResponse toResponse(Seat seat) {
        return SeatResponse.builder()
                .seatNumber(seat.getSeatNumber())
                .seatClass(seat.getSeatClass().getDisplayName())
                .price(seat.getPrice())
                .seatStatus(seat.getSeatStatus().getDisplayName())
                .build();
    }

    public Set<SeatResponse> toResponseSet(Set<Seat> seats) {
        return seats.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }
}
