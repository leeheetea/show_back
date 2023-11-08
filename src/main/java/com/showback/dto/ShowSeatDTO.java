package com.showback.dto;

import com.showback.model.Seat;
import lombok.Data;

@Data
public class ShowSeatDTO {

    private Long showSeatId;

    private boolean canReservation;

    private Long seatId;

    private Long showId;

}
