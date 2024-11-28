package com.flightreservation.dto.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Value
public class CreateOrderRequest {
    @NotNull(message = "User ID cannot be null")
    Long userId;

    @NotEmpty(message = "Seat numbers must not be empty")
    List<String> seatNumbers;

    @NotNull(message = "Flight ID cannot be null")
    Long flightId;
}