package com.showback.dto;

import lombok.Data;


@Data
public class OrderDetailDTO {

    private Long orderDetailId;

    private int finalPrice;

    private Long orderId;

    private ShowSeatDTO showSeats;

}
