package com.flightreservation.service.flight;

import com.flightreservation.dto.request.AddFlightRequest;
import com.flightreservation.dto.request.UpdateFlightRequest;
import com.flightreservation.dto.response.FlightResponse;
import com.flightreservation.entity.Flight;

import java.util.List;

public interface IFlightService {

    List<FlightResponse> getAllFlights();

    FlightResponse addFlight(AddFlightRequest addFlightRequest);

    FlightResponse updateFlight(Long flightId, UpdateFlightRequest updateFlightRequest);

    Flight getFlightById(Long flightId);

    void deleteFlight(Long flightId);

    FlightResponse getFlight(Long flightId);
}
