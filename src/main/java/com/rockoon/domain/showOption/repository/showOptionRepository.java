package com.rockoon.domain.showOption.repository;

import com.rockoon.domain.showOption.entity.ShowOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface showOptionRepository extends JpaRepository<ShowOption, Long> {
    List<ShowOption> findOptionsByBoardId(Long boardId);

    void deleteAllByBoardId(Long boardId);
}
