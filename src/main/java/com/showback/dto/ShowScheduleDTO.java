package com.showback.dto;

import com.showback.model.ShowSchedule;
import lombok.Data;

import java.util.Date;

@Data
public class ShowScheduleDTO {

    private Long scheduleId;

    private Date scheduleDate;

    private Date scheduleTime;

    private Long showId;

}
