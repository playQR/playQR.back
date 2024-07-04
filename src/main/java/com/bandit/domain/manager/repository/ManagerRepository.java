package com.bandit.domain.manager.repository;

import com.bandit.domain.board.entity.Board;
import com.bandit.domain.manager.entity.Manager;
import com.bandit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    List<Manager> findByBoard(Board board);
    Optional<Manager> findByBoardAndMember(Board board, Member member);
}
