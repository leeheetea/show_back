package com.showback.repository;


import com.showback.model.ShowBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowBannerRepository extends JpaRepository<ShowBanner, Long> {

    List<ShowBanner> findByBannerUrlIsNotNull();
}
