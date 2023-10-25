package com.showback.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    private Long orderId;

    private int ticketAmount;

    private String orderState;

    private Date orderDate;

    private Long userAuthId;

    private List<OrderDetailDTO> orderDetails;

    private ReservationDTO reservation;

}
