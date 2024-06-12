package com.rockoon.domain.ticket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    private String data;
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
}
