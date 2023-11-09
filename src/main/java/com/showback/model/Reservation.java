package com.showback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.showback.domain.OrderState;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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

    private LocalDate date;

    private OrderState state;

    private String showName;

    private String showVenue;

    private int price;

    private int ticketAmount;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonBackReference
    private Show show;
}
