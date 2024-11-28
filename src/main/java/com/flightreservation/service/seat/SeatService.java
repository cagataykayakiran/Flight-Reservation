package com.flightreservation.service.seat;

import com.flightreservation.dto.request.SeatDetailRequest;
import com.flightreservation.dto.response.SeatResponse;
import com.flightreservation.entity.Flight;
import com.flightreservation.entity.Seat;
import com.flightreservation.enums.SeatClass;
import com.flightreservation.enums.SeatStatus;
import com.flightreservation.exception.NotFoundException;
import com.flightreservation.mapper.SeatMapper;
import com.flightreservation.repository.SeatRepository;
import com.flightreservation.service.flight.FlightService;
import com.flightreservation.service.flight.IFlightService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeatService implements ISeatService {

    private final SeatRepository seatRepository;
    private final IFlightService flightService;
    private final SeatMapper seatMapper;

    public SeatService(SeatRepository seatRepository, @Lazy FlightService flightService, SeatMapper seatMapper) {
        this.seatRepository = seatRepository;
        this.flightService = flightService;
        this.seatMapper = seatMapper;
    }

    @Override
    public List<SeatResponse> getAllSeatsByFlight(Long flightId) {
        return seatRepository.findByFlightId(flightId).stream()
                .map(seatMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatResponse addSeat(SeatClass seatClass, BigDecimal price, Long flightId) {
        var flight = flightService.getFlightById(flightId);

        Integer currentMaxSeatPosition = getCurrentMaxSeatPosition(flightId);
        Integer newSeatPosition = currentMaxSeatPosition + 1;

        Seat seat = createSeat(newSeatPosition, seatClass, price, flight);
        return seatMapper.toResponse(seatRepository.save(seat));
    }

    @Transactional
    @Override
    public Set<Seat> addSeatsToFlight(Long flightId, List<SeatDetailRequest> seatDetailRequests) {
        if (seatDetailRequests == null || seatDetailRequests.isEmpty()) {
            throw new IllegalArgumentException("Seat details must not be empty");
        }

        var flight = flightService.getFlightById(flightId);

        int currentMaxSeatPosition = getCurrentMaxSeatPosition(flightId);
        Set<Seat> seatEntities = new HashSet<>();

        for (SeatDetailRequest detail : seatDetailRequests) {
            SeatClass seatClass;
            try {
                seatClass = SeatClass.valueOf(detail.getSeatClass().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid seat class: " + detail.getSeatClass());
            }

            for (int i = 1; i <= detail.getCount(); i++) {
                Integer newSeatPosition = currentMaxSeatPosition + 1;
                Seat seat = createSeat(newSeatPosition, seatClass, detail.getPrice(), flight);
                seatEntities.add(seat);
                currentMaxSeatPosition++;
            }
        }

        seatRepository.saveAll(seatEntities);
        return seatEntities;
    }

    @Override
    public void deleteSeat(Long seatId, Long flightId) {
        Seat seat = getSeatById(seatId);

        if (!seat.getFlight().getId().equals(flightId)) {
            throw new IllegalArgumentException("Seat does not belong to the specified flight.");
        }

        seatRepository.delete(seat);
    }

    @Override
    public SeatResponse updateSeat(Long seatId, BigDecimal price, SeatClass seatClass) {
        Seat seat = getSeatById(seatId);

        if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
            seat.setPrice(price);
        } else {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }

        if (seatClass != null) {
            seat.setSeatClass(seatClass);
        }

        return seatMapper.toResponse(seatRepository.save(seat));
    }

    @Override
    public Seat getSeatById(Long seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new NotFoundException("Seat not found with ID: " + seatId));
    }

    @Override
    public List<Seat> findAndLockSeatsByNumbersAndFlightId(List<String> seatNumbers, Long flightId) {
        List<Seat> seats = seatRepository.findAndLockBySeatNumbersAndFlightId(seatNumbers, flightId);
        if (seats.size() != seatNumbers.size()) {
            throw new NotFoundException("Not all seats found for flight ID: " + flightId);
        }
        return seats;
    }

    @Override
    public void save(Seat seat) {
        seatRepository.save(seat);
    }

    private Integer getCurrentMaxSeatPosition(Long flightId) {
        Integer currentMaxSeatPosition = seatRepository.findMaxSeatPositionByFlightId(flightId);
        return currentMaxSeatPosition != null ? currentMaxSeatPosition : 0;
    }

    private Seat createSeat(Integer seatPosition, SeatClass seatClass, BigDecimal price, Flight flight) {
        String seatNumber = "A" + seatPosition;

        return Seat.builder()
                .seatPosition(seatPosition)
                .seatNumber(seatNumber)
                .seatClass(seatClass)
                .price(price != null ? price : BigDecimal.ZERO)
                .seatStatus(SeatStatus.AVAILABLE)
                .flight(flight)
                .build();
    }
}

