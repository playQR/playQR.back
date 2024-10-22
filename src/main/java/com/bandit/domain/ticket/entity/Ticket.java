package com.bandit.domain.ticket.entity;

import com.bandit.domain.auditing.entity.BaseTimeEntity;
import com.bandit.domain.board.entity.Promotion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "ticket")
public class Ticket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    @PrePersist
    private void generateData() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }

    public static Ticket of(Promotion promotion, LocalDate dueDate) {
        return Ticket.builder()
                .promotion(promotion)
                .dueDate(dueDate)
                .build();
    }

    public void update(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
