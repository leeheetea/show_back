package com.showback.repository;


import com.showback.model.ShowBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowBannerRepository extends JpaRepository<ShowBanner, Long> {
}
