package com.showback.model;

import com.showback.dto.OrderDetailDTO;
import lombok.Data;

@Data
public class SelectedSeatsDTO {

    private Long seatId;
    private int price;

    public OrderDetailDTO toOrderDetailDTO(){
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setSeatId(this.seatId);
        orderDetailDTO.setPrice(this.price);

        return orderDetailDTO;
    }
}
