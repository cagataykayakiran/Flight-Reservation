package com.flightreservation.controller;

import com.flightreservation.common.ApiResponse;
import com.flightreservation.dto.request.AddFlightRequest;
import com.flightreservation.dto.request.UpdateFlightRequest;
import com.flightreservation.service.flight.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/allFlights")
    public ResponseEntity<ApiResponse> getAllFlights() {
        var flights = flightService.getAllFlights();
        return ResponseEntity.ok(ApiResponse.builder().data(flights).message("Flights fetched successfully").build());
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<ApiResponse> getAllFlights(@PathVariable Long flightId) {
        var flights = flightService.getFlight(flightId);
        return ResponseEntity.ok(ApiResponse.builder().data(flights).message("Flights fetched successfully").build());
    }

    @PostMapping("/addFlight")
    public ResponseEntity<ApiResponse> addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        var flight = flightService.addFlight(addFlightRequest);
        return ResponseEntity.ok(ApiResponse.builder().data(flight).message("Flight added successfully").build());
    }

    @DeleteMapping("/deleteFlight/{flightId}")
    public ResponseEntity<ApiResponse> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.ok(ApiResponse.builder().message("Flight deleted successfully").build());
    }

    @PutMapping("/updateFlight/{flightId}")
    public ResponseEntity<ApiResponse> updateFlight(
            @PathVariable Long flightId,
            @Valid @RequestBody UpdateFlightRequest updateFlightRequest
    ) {
        var flight = flightService.updateFlight(flightId, updateFlightRequest);
        return ResponseEntity.ok(ApiResponse.builder().data(flight).message("Flight updated successfully").build());
    }
}
