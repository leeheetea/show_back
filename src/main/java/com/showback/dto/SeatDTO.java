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

    private List<Long> showSeatIds;

}
