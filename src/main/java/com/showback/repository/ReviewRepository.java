package com.showback.repository;

import com.showback.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.show.showId = :showId ORDER BY r.id DESC")
    List<Review> findAllByShowId(@Param("showId") Long showId);

    @Query("SELECT r FROM Review r, UserAuth ua where r.showId = :showId ORDER BY r.id DESC")
    List<Review> findAllByShowIdAndUserId(@Param("showId") Long showId, @Param("authId") Long authId);

}
