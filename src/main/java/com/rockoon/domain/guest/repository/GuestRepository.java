package com.rockoon.domain.guest.repository;

import com.rockoon.domain.guest.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
