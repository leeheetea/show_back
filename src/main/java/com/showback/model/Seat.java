package com.showback.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private int seatRow;

    private int seatColumn;

    @OneToMany(mappedBy = "seat")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "seat")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "seat")
    private List<ShowSeat> showSeats = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

}
