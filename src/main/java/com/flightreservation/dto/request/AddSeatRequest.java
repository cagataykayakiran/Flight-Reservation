package com.flightreservation.dto.request;

import com.flightreservation.enums.SeatClass;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Value
public class AddSeatRequest {

    @NotNull(message = "Seat class cannot be null")
    SeatClass seatClass;

    @DecimalMin(value = "0.0", message = "Price must not be negative")
    BigDecimal price;

    @NotNull(message = "Flight ID cannot be null")
    Long flightId;
}