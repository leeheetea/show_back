package com.showback.dto;

import com.showback.domain.OrderState;
import lombok.Data;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ReservationDTO {

    private Long reservationId;

    private LocalDate reservationDate;

    private OrderState reservationState;

    private String reservationShowName;

    private Date reservationShowDate;

    private String reservationShowVenue;

    private int reservationPrice;

    private int reservationTicketAmount;

    private Long orderId;

    private Long showId;
}
