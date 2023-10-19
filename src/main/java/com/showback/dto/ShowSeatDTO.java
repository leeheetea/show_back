package com.showback.dto;

import lombok.Data;

@Data
public class ShowSeatDTO {

    private Long showSeatId;

    private boolean canReservation;

    private Long showId;

    private Long seatId;
}
