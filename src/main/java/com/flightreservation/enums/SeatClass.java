package com.flightreservation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatClass {
    ECONOMY("Economy"),
    BUSINESS("Business");

    private final String displayName;
}
