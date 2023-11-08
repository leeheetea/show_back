package com.showback.repository;

import com.showback.model.UserAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAgreementRepository extends JpaRepository<UserAgreement, Long> {
}
