package com.flightreservation.controller;

import com.flightreservation.common.ApiResponse;
import com.flightreservation.dto.request.CreateOrderRequest;
import com.flightreservation.dto.response.OrderResponse;
import com.flightreservation.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody CreateOrderRequest request) {
        OrderResponse orderResponse = orderService.createOrder(request.getUserId(), request.getSeatNumbers(), request.getFlightId());
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Order created successfully")
                .data(orderResponse)
                .build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        List<OrderResponse> userOrders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User orders retrieved successfully")
                .data(userOrders)
                .build());
    }

}
