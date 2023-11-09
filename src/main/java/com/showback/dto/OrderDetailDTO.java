package com.showback.dto;

import lombok.Data;

import java.util.List;


@Data
public class OrderDetailDTO {

    private Long orderDetailId;
    private Long orderId;

    private int price;
    private Long seatId;

}
