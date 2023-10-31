package com.showback.dto;

import com.showback.model.Seat;
import lombok.Data;

@Data
public class ShowSeatDTO {

    private Long showSeatId;

    private boolean canReservation;

    private SeatDTO seatDTO;

    private Long showId;

}
