package com.flightreservation.dto.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
public class AddFlightRequest {

    @NotBlank(message = "Flight name is required")
    String name;

    @NotBlank(message = "Flight name is required")
    String description;

    @NotBlank(message = "Departure location is required")
    String departureLocation;

    @NotBlank(message = "Arrival location is required")
    String arrivalLocation;

    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be in the future")
    LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Future(message = "Arrival time must be in the future")
    LocalDateTime arrivalTime;

    @NotBlank(message = "Airline is required")
    String airline;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    BigDecimal price;

    @NotEmpty(message = "Seat details are required")
    List<SeatDetailRequest> seatDetailRequests;
}
