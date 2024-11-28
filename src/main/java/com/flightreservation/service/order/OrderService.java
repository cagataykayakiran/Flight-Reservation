package com.flightreservation.service.order;

import com.flightreservation.dto.response.OrderResponse;
import com.flightreservation.entity.Flight;
import com.flightreservation.entity.User;
import com.flightreservation.entity.Order;
import com.flightreservation.entity.Seat;
import com.flightreservation.enums.SeatStatus;
import com.flightreservation.mapper.OrderMapper;
import com.flightreservation.repository.OrderRepository;
import com.flightreservation.service.seat.ISeatService;
import com.flightreservation.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final IUserService userService;
    private final ISeatService seatService;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderResponse createOrder(Long userId, List<String> seatNumbers, Long flightId) {
        if (seatNumbers.isEmpty()) {
            throw new IllegalArgumentException("Seat numbers must not be empty or nullable.");
        }

        User user = getUser(userId);

        List<Seat> seats = getAndLockSeats(seatNumbers, flightId);

        checkSeatAvailability(seats);

        BigDecimal totalAmount = calculateTotalAmount(seats);

        Order order = buildOrder(user, seats, totalAmount);

        Order savedOrder = saveOrder(order);

        updateSeatStatuses(seats);

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        User user = userService.findUserById(userId);
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userService.findUserById(userId);
    }

    private List<Seat> getAndLockSeats(List<String> seatNumbers, Long flightId) {
        return seatService.findAndLockSeatsByNumbersAndFlightId(seatNumbers, flightId);
    }

    private void checkSeatAvailability(List<Seat> seats) {
        for (Seat seat : seats) {
            if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
                throw new IllegalArgumentException("Seat " + seat.getSeatNumber() + " is not available.");
            }
        }
    }

    private BigDecimal calculateTotalAmount(List<Seat> seats) {
        if (seats.isEmpty()) {
            return BigDecimal.ZERO;
        }

        Flight flight = seats.get(0).getFlight();
        BigDecimal flightPrice = flight.getPrice();

        BigDecimal seatsTotal = seats.stream()
                .map(Seat::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return seatsTotal.add(flightPrice);
    }

    private Order buildOrder(User user, List<Seat> seats, BigDecimal totalAmount) {
        return Order.builder()
                .purchaseTime(LocalDateTime.now())
                .totalAmount(totalAmount)
                .user(user)
                .seats(seats)
                .build();
    }

    private Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    private void updateSeatStatuses(List<Seat> seats) {
        for (Seat seat : seats) {
            seat.setSeatStatus(SeatStatus.SOLD);
            seatService.save(seat);
        }
    }
}

