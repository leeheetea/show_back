package com.showback.service;

import com.showback.model.TermsOfService;
import com.showback.repository.TermsOfServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TermsOfServiceService {

    private final TermsOfServiceRepository termsOfServiceRepository;

    @PostConstruct
    public void initializeTerms() {
        if (termsOfServiceRepository.count() == 0) {
            createTerm("term1", "[필수] 이용약관");
            createTerm("term2", "[필수] 전자금융거래 이용약관");
            createTerm("term3", "[필수] 개인정보 수집동의서");
            createTerm("term4", "[필수] 개인정보 제 3자 제공동의");
            createTerm("term5", "[필수] 이용약관");
            createTerm("term6", "[필수] 개인정보 수집동의서");
            createTerm("term7", "[필수] 개인정보 제 3자 제공동의");
            createTerm("term8", "[선택] 개인정보 수집동의서");
            createTerm("term9", "[선택] 위치기반 서비스 이용약관");
            createTerm("term10", "[선택] 제 3자 마케팅 활용동의서 전체동의");
        }
    }

    private void createTerm(String termCode, String title) {
        TermsOfService termsOfService = new TermsOfService();
        termsOfService.setTermCode(termCode);
        termsOfService.setTitle(title);
        termsOfServiceRepository.save(termsOfService);
    }
}
