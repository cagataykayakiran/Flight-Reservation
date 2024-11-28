package com.flightreservation.service.order;

import com.flightreservation.dto.response.OrderResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOrderService {

    @Transactional
    OrderResponse createOrder(Long userId, List<String> seatNumbers, Long flightId);

    List<OrderResponse> getOrdersByUserId(Long userId);
}