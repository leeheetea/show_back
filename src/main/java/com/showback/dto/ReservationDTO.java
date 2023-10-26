package com.showback.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReservationDTO {

    private Long reservationId;

    private Date reservationDate;

    private String reservationState;

    private String reservationShowName;

    private Date reservationShowDate;

    private String reservationShowVenue;

    private int reservationPrice;

    private int reservationTicketAmount;

    private Long orderId;

    private Long showId;
}
