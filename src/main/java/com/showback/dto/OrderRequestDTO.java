package com.showback.dto;

import com.showback.model.SelectedSeatsDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class OrderRequestDTO {

    private Long showId;

    private Long userId;

    private LocalDate date;

    private LocalTime time;

    private List<SelectedSeatsDTO> selectedSeats;
}
