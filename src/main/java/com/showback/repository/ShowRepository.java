package com.showback.repository;

import com.showback.dto.ShowDTO;
import com.showback.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query("SELECT s FROM Show s WHERE s.title LIKE %:keyword%")
    List<Show> searchShowsByKeyword(String keyword);

    @Query("SELECT s FROM Show s WHERE s.title LIKE %:keyword% AND s.type = :type")
    List<Show> searchShowsByKeywordAndType(String keyword, String type);

}
