package com.showback.dto;

import lombok.Data;

@Data
public class SeatDTO {

    private Long seatId;

    private int seatRow;

    private int seatColumn;

    private Long venueId;

    // id 전송이 필요하다면 추가

    // private List<Long> reservationIds = new ArrayList<>();
    // private List<Long> showSeatIds = new ArrayList<>();

}
