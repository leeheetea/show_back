package com.showback.repository;

import com.showback.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long>, ShowSeatCustomRepository {

//    @Query("SELECT ss FROM ShowSeat ss " +
//            "JOIN ss.show sh " +
//            "JOIN sh.showSchedules sc " +
//            "WHERE sh.showId = :showId AND sc.scheduleDate = :showDate AND sc.scheduleTime = :showTime")
//    List<ShowSeat> findShowSeatsByShowAndSchedule(
//            @Param("showId") Long showId,
//            @Param("showDate") Date showDate,
//            @Param("showTime") Time showTime);


}
