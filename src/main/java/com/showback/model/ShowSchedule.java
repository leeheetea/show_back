package com.showback.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "shows_schedule")
@Data
public class ShowSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private Date scheduleDate;

    private Date scheduleTime;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}
