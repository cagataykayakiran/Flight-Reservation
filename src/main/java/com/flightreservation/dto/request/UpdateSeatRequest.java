package com.flightreservation.dto.request;

import com.flightreservation.enums.SeatClass;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Value
public class UpdateSeatRequest {

    @NotNull(message = "Seat class cannot be null")
    SeatClass seatClass;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    BigDecimal price;
}
