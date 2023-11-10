package com.showback.dto;

import com.showback.domain.OrderState;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    private Long orderId;

    private int ticketAmount;

    private OrderState orderState;

    private LocalDate orderDate;

    private Long userAuthId;

    private List<OrderDetailDTO> orderDetails;

    private ReservationDTO reservation;

}
