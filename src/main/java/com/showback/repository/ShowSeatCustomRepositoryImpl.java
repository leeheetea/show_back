package com.showback.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.showback.model.ShowSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static com.showback.model.QShow.show;
import static com.showback.model.QShowSchedule.showSchedule;
import static com.showback.model.QShowSeat.showSeat;

@Repository
@RequiredArgsConstructor
public class ShowSeatCustomRepositoryImpl implements ShowSeatCustomRepository  {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ShowSeat> findShowSeatsByShowAndSchedule(Long showId, LocalDate showDate, LocalTime showTime) {
        return queryFactory
                .selectFrom(showSeat)
                .join(showSeat.show, show)
                .join(show.showSchedules, showSchedule)
                .where(
                        show.showId.eq(showId),
                        showSchedule.scheduleDate.eq(showDate),
                        showSchedule.scheduleTime.eq(showTime)
                )
                .fetch();
    }
}
