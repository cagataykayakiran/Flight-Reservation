package com.flightreservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class FlightInfoResponse {

    Long flightId;
    String flightName;
    String departureLocation;
    String arrivalLocation;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime arrivalTime;
    String airline;
    BigDecimal price;
}
