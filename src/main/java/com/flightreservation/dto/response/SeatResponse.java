package com.flightreservation.dto.response;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
public class SeatResponse {
    String seatNumber;
    String seatClass;
    BigDecimal price;
    String seatStatus;
}