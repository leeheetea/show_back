package com.showback.dto;

import com.showback.domain.OrderState;
import lombok.Data;

import java.util.List;

@Data
public class ReservationResponseDTO {

    Long reservationId;
    String userName;
    String venueName;
    List<SeatDTO> seat;
    String showImgUrl;
    OrderState orderState;

}
