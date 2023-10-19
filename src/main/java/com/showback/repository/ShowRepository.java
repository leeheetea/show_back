package com.showback.repository;

import com.showback.dto.ShowDTO;
import com.showback.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    Show findByShowId(Long showId);

}
