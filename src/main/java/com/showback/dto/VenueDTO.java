package com.showback.dto;

import lombok.Data;

import java.util.List;

@Data
public class VenueDTO {

    private Long venueId;

    private String venueName;

    private String venueAddress;

    private int seatMaxRow;

    private int seatMaxColumn;

    private List<SeatDTO> seats;

    private List<ShowDTO> shows;

}
