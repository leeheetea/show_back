package com.showback.model;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private int ticketAmount;

    private String orderState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id")
    private UserAuth userAuth;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<Reservation> reservations = new ArrayList<>();
}
