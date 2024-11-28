package com.flightreservation.mapper;

import com.flightreservation.dto.response.FlightInfoResponse;
import com.flightreservation.dto.response.OrderResponse;
import com.flightreservation.entity.Order;
import com.flightreservation.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .purchaseTime(order.getPurchaseTime())
                .totalAmount(order.getTotalAmount())
                .userId(order.getUser().getId())
                .seatNumbers(mapSeatNumbers(order.getSeats()))
                .flights(mapFlights(order.getSeats()))
                .build();
    }

    private List<String> mapSeatNumbers(List<Seat> seats) {
        return seats.stream()
                .map(Seat::getSeatNumber)
                .collect(Collectors.toList());
    }

    private List<FlightInfoResponse> mapFlights(List<Seat> seats) {
        return seats.stream()
                .map(this::mapFlightInfo)
                .distinct()
                .collect(Collectors.toList());
    }

    private FlightInfoResponse mapFlightInfo(Seat seat) {
        return FlightInfoResponse.builder()
                .flightId(seat.getFlight().getId())
                .flightName(seat.getFlight().getFlightName())
                .departureLocation(seat.getFlight().getDepartureLocation())
                .arrivalLocation(seat.getFlight().getArrivalLocation())
                .departureTime(seat.getFlight().getDepartureTime())
                .arrivalTime(seat.getFlight().getArrivalTime())
                .airline(seat.getFlight().getAirline())
                .price(seat.getFlight().getPrice())
                .build();
    }
}


