package com.flightreservation.dto.request;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Value
public class SeatDetailRequest {
    @NotBlank(message = "Seat class is required")
    String seatClass;

    @NotNull(message = "Seat price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    BigDecimal price;

    @NotNull(message = "Seat count is required")
    @Min(value = 1, message = "Seat count must be at least 1")
    int count;
}
