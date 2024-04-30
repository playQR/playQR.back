package com.rockoon.domain.board.chatting.repository;

import com.rockoon.domain.board.chatting.entity.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepository extends JpaRepository<Chatting, Long> {

}
