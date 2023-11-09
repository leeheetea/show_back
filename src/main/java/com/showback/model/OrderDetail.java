package com.showback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "orders_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    private int finalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    // 변경: @OneToOne에서 @ManyToOne으로 변경합니다.
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "show_seat_id")
    @JsonBackReference
    private ShowSeat showSeat;

    @Version
    private Long version;
}

