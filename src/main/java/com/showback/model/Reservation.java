package com.showback.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "reservations")
public class Reservation {

    @Id
    private Long reservationId;

    private Date reservationDate;

    private String reservationState;

    private String reservationShowName;

    private Date reservationShowDate;

    private String reservationShowVenue;

    private String reservationPrice;

    private int reservationTicketAmount;

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
