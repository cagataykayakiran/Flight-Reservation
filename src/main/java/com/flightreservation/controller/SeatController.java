package com.flightreservation.controller;


import com.flightreservation.common.ApiResponse;
import com.flightreservation.dto.request.AddSeatRequest;
import com.flightreservation.dto.request.UpdateSeatRequest;
import com.flightreservation.service.seat.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/allSeats/{flightId}")
    public ResponseEntity<ApiResponse> getAllSeats(@PathVariable Long flightId) {
        var seats = seatService.getAllSeatsByFlight(flightId);
        return ResponseEntity.ok(ApiResponse.builder()
                .data(seats)
                .message("Seats fetched successfully")
                .build());
    }

    @PostMapping("/addSeat")
    public ResponseEntity<ApiResponse> addSeat(@Valid @RequestBody AddSeatRequest addSeatRequest) {
        var seat = seatService.addSeat(
                addSeatRequest.getSeatClass(),
                addSeatRequest.getPrice(),
                addSeatRequest.getFlightId()
        );
        return ResponseEntity.ok(ApiResponse.builder()
                .data(seat)
                .message("Seat added successfully")
                .build());
    }

    @DeleteMapping("/deleteSeat")
    public ResponseEntity<ApiResponse> deleteSeat(@RequestParam Long seatId , @RequestParam Long flightId) {
        seatService.deleteSeat(seatId, flightId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Seat deleted successfully")
                .build());
    }

    @PutMapping("/updateSeat/{seatId}")
    public ResponseEntity<ApiResponse> updateSeat(
            @PathVariable Long seatId,
            @Valid @RequestBody UpdateSeatRequest updateSeatRequest
    ) {
        var seatResponse = seatService.updateSeat(
                seatId,
                updateSeatRequest.getPrice(),
                updateSeatRequest.getSeatClass()
        );
        return ResponseEntity.ok(ApiResponse.builder()
                .data(seatResponse)
                .message("Seat updated successfully")
                .build());
    }
}
