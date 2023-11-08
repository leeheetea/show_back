package com.showback.repository;

import com.showback.model.TermsOfService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsOfServiceRepository extends JpaRepository<TermsOfService, Long> {
    TermsOfService findByTermCode(String termCode);
}
