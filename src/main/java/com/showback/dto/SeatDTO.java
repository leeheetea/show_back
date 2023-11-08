package com.showback.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;

@Data
public class SeatDTO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private int seatRow;

    private int seatColumn;

    private Long venueId;

    private List<Long> showSeatIds;

}
