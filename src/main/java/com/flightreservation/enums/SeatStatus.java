package com.flightreservation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatStatus {
    AVAILABLE("Available"),
    SOLD("Sold");

    private final String displayName;
}
