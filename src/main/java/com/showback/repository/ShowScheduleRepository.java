package com.showback.repository;

import com.showback.model.Show;
import com.showback.model.ShowSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowScheduleRepository extends JpaRepository<ShowSchedule, Long> {

    ShowSchedule findByShow(Show show);

}
