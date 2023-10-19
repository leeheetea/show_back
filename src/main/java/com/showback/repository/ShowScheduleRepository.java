package com.showback.repository;

import com.showback.model.Show;
import com.showback.model.ShowSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowScheduleRepository extends JpaRepository<ShowSchedule, Long> {

    ShowSchedule findByShow(Show show);

}
