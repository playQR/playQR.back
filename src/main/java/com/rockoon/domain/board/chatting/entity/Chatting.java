package com.rockoon.domain.board.chatting.entity;

import com.rockoon.domain.board.entity.Board;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@DiscriminatorValue("type_chatting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatting extends Board {
}
