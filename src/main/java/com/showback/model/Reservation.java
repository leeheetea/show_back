package com.showback.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "reservations")
public class Reservation {

    @Id
    private Long reservationId;

    private Date reservationDate;

    private String reservationState;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}
