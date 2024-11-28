package com.flightreservation.dto.response;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
public class OrderResponse {
    Long orderId;
    LocalDateTime purchaseTime;
    BigDecimal totalAmount;
    Long userId;
    List<String> seatNumbers;
    List<FlightInfoResponse> flights;
}
