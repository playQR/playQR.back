package com.rockoon.domain.option.repository;

import com.rockoon.domain.option.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findOptionsByBoardId(Long boardId);
}
