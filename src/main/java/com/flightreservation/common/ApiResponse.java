package com.flightreservation.common;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ApiResponse {

    Object data;
    LocalDateTime timestamp = LocalDateTime.now();
    String message;
}
