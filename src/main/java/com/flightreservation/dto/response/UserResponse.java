package com.flightreservation.dto.response;

import lombok.*;

@Value
@Builder
public class UserResponse {
    Long id;
    String firstName;
    String lastName;
    String email;
}

