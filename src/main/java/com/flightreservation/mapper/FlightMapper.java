package com.flightreservation.mapper;

import com.flightreservation.dto.request.AddFlightRequest;
import com.flightreservation.dto.response.FlightResponse;
import com.flightreservation.entity.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightMapper {

    private final SeatMapper seatMapper;

    public FlightResponse toResponse(Flight flight) {
        return FlightResponse.builder()
                .flightName(flight.getFlightName())
                .description(flight.getDescription())
                .price(flight.getPrice())
                .departureLocation(flight.getDepartureLocation())
                .arrivalLocation(flight.getArrivalLocation())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .airline(flight.getAirline())
                .seats(seatMapper.toResponseSet(flight.getSeats()))
                .build();
    }

    public Flight toEntity(AddFlightRequest request) {
        return Flight.builder()
                .flightName(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .departureLocation(request.getDepartureLocation())
                .arrivalLocation(request.getArrivalLocation())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .airline(request.getAirline())
                .build();
    }
}

