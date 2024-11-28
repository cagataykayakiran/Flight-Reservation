package com.flightreservation.repository;

import com.flightreservation.common.BaseRepository;
import com.flightreservation.entity.User;

public interface UserRepository extends BaseRepository<User> {
    boolean existsByEmail(String email);
}
