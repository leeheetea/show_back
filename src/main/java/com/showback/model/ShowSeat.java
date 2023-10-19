package com.showback.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "shows_seats")
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showSeatId;

    private boolean canReservation;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
}
