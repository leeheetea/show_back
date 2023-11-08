package com.showback.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "orders_details")
public class OrderDetail {

    @Id
    private Long orderDetailId;

    private int finalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
}
