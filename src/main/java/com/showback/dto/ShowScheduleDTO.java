package com.showback.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShowScheduleDTO {

    private Long scheduleId;

    private LocalDate scheduleDate;

    private LocalTime scheduleTime;

    private Long showId;

}
