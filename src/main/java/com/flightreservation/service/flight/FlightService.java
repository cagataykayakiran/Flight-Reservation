package com.flightreservation.service.flight;

import com.flightreservation.dto.request.AddFlightRequest;
import com.flightreservation.dto.request.UpdateFlightRequest;
import com.flightreservation.dto.response.FlightResponse;
import com.flightreservation.entity.Flight;
import com.flightreservation.entity.Seat;
import com.flightreservation.exception.NotFoundException;
import com.flightreservation.mapper.FlightMapper;
import com.flightreservation.repository.FlightRepository;
import com.flightreservation.service.seat.ISeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService implements IFlightService {

    private final FlightRepository flightRepository;
    private final ISeatService seatService;
    private final FlightMapper flightMapper;

    @Override
    public List<FlightResponse> getAllFlights() {
        var flights = flightRepository.findAll();
        return flights.stream()
                .map(flightMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public FlightResponse addFlight(AddFlightRequest addFlightRequest) {
        Flight flight = flightMapper.toEntity(addFlightRequest);
        flight = flightRepository.save(flight);

        Set<Seat> seats = seatService.addSeatsToFlight(flight.getId(), addFlightRequest.getSeatDetailRequests());
        flight.getSeats().addAll(seats);

        return flightMapper.toResponse(flightRepository.save(flight));
    }

    @Transactional
    @Override
    public FlightResponse updateFlight(Long flightId, UpdateFlightRequest updateFlightRequest) {
        Flight flight = getFlightById(flightId);

        flight.setFlightName(updateFlightRequest.getFlightName());
        flight.setDescription(updateFlightRequest.getDescription());
        flight.setDepartureLocation(updateFlightRequest.getDepartureLocation());
        flight.setArrivalLocation(updateFlightRequest.getArrivalLocation());
        flight.setDepartureTime(updateFlightRequest.getDepartureTime());
        flight.setArrivalTime(updateFlightRequest.getArrivalTime());
        flight.setAirline(updateFlightRequest.getAirline());
        flight.setPrice(updateFlightRequest.getPrice());

        Flight updatedFlight = flightRepository.save(flight);

        return flightMapper.toResponse(updatedFlight);
    }

    @Override
    public Flight getFlightById(Long flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new NotFoundException("Flight not found with ID: " + flightId));
    }

    @Override
    public void deleteFlight(Long flightId) {
        var flight = getFlightById(flightId);
        flightRepository.delete(flight);
    }

    @Override
    public FlightResponse getFlight(Long flightId) {
        var flight = getFlightById(flightId);
        return flightMapper.toResponse(flight);
    }
}

