package com.flightreservation.repository;

import com.flightreservation.common.BaseRepository;
import com.flightreservation.entity.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    List<Order> findByUserId(Long userId);
}
