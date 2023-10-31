package com.showback.repository;

import com.showback.model.ShowSeat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ShowSeatCustomRepository {
    List<ShowSeat> findShowSeatsByShowAndSchedule(Long showId, LocalDate showDate, LocalTime showTime);
}
