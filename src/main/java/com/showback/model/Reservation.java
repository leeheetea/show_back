package com.showback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "reservations")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private Date reservationDate;

    private String reservationState;

    private String reservationShowName;

    private String reservationShowVenue;

    private int reservationPrice;

    private int reservationTicketAmount;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonBackReference
    private Show show;
}
