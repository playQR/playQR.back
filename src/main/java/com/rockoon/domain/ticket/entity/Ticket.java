package com.rockoon.domain.ticket.entity;

import com.rockoon.domain.auditing.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "ticket")
public class Ticket extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
}
