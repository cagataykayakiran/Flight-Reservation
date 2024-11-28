package com.flightreservation.dto.request;

import lombok.Builder;

import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Value
public class UpdateFlightRequest {
    @NotBlank(message = "Flight name cannot be blank")
    String flightName;

    @NotBlank(message = "Description cannot be blank")
    String description;

    @NotBlank(message = "Departure location cannot be blank")
    String departureLocation;

    @NotBlank(message = "Arrival location cannot be blank")
    String arrivalLocation;

    @NotNull(message = "Departure time cannot be null")
    LocalDateTime departureTime;

    @NotNull(message = "Arrival time cannot be null")
    LocalDateTime arrivalTime;

    @NotBlank(message = "Airline cannot be blank")
    String airline;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    BigDecimal price;
}
