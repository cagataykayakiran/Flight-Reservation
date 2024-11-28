package com.flightreservation;

import com.flightreservation.entity.Flight;
import com.flightreservation.entity.Seat;
import com.flightreservation.entity.User;
import com.flightreservation.enums.SeatClass;
import com.flightreservation.enums.SeatStatus;
import com.flightreservation.repository.FlightRepository;
import com.flightreservation.repository.SeatRepository;
import com.flightreservation.repository.UserRepository;
import com.flightreservation.service.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class OrderServiceConcurrencyTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    private Seat seat;
    private User user1;
    private User user2;
    private Flight flight;

    @BeforeEach
    public void setUp() {
        flight = Flight.builder()
                .flightName("Test Flight")
                .description("Test Flight Description")
                .airline("Test Airline")
                .arrivalLocation("Test")
                .departureLocation("Test")
                .arrivalTime(LocalDateTime.now().plusHours(1))
                .departureTime(LocalDateTime.now())
                .price(new BigDecimal("1000"))
                .build();
        flightRepository.save(flight);

        seat = Seat.builder()
                .seatNumber("A1")
                .seatClass(SeatClass.ECONOMY)
                .price(new BigDecimal("100"))
                .seatPosition(1)
                .seatStatus(SeatStatus.AVAILABLE)
                .flight(flight)
                .build();
        seatRepository.save(seat);

        user1 = User.builder()
                .firstName("Alice")
                .lastName("")
                .email("alice@example.com")
                .password("password123")
                .build();
        userRepository.save(user1);

        user2 = User.builder()
                .firstName("Bob")
                .lastName("")
                .email("bob@example.com")
                .password("password123")
                .build();
        userRepository.save(user2);
    }

    @Test
    public void testConcurrentSeatPurchase() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1);

        Runnable task1 = () -> {
            try {
                latch.await();
                orderService.createOrder(user1.getId(), List.of(seat.getSeatNumber()), flight.getId());
                System.out.println("User 1 successfully purchased the seat.");
            } catch (Exception e) {
                System.out.println("User 1 failed to purchase the seat: " + e.getMessage());
            }
        };

        Runnable task2 = () -> {
            try {
                latch.await();
                orderService.createOrder(user2.getId(), List.of(seat.getSeatNumber()), flight.getId());
                System.out.println("User 2 successfully purchased the seat.");
            } catch (Exception e) {
                System.out.println("User 2 failed to purchase the seat: " + e.getMessage());
            }
        };

        executorService.submit(task1);
        executorService.submit(task2);

        latch.countDown();

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }

        Seat updatedSeat = seatRepository.findById(seat.getId()).orElseThrow();
        System.out.println("Seat status: " + updatedSeat.getSeatStatus());
    }
}
