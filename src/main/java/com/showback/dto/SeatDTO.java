package com.showback.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SeatDTO {

    private Long seatId;

    private int seatRow;

    private int seatColumn;

    private Long venueId;

    private List<Long> orderDetailIds;

    private List<Long> reservationIds;

    private List<Long> showSeatIds;

}
