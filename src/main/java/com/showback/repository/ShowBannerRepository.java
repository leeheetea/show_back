package com.showback.repository;


import com.showback.model.ShowBanner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowBannerRepository extends JpaRepository<ShowBanner, Long> {

        @Query("SELECT sb FROM ShowBanner sb WHERE sb.bannerUrl IS NOT NULL AND sb.bannerUrl <> ''")
        List<ShowBanner> findByBannerUrlIsNotNullAndNotEmpty(Pageable pageable);

        @Query("SELECT sb FROM ShowBanner sb WHERE sb.smallBannerUrl IS NOT NULL AND sb.smallBannerUrl <> ''")
        List<ShowBanner> findBySmallBannerUrlIsNotNullAndNotEmpty(Pageable pageable);
}

