package com.rockoon.domain.option.repository;

import com.rockoon.domain.option.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
