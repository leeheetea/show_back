package com.showback.model;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "terms_of_service")
@Data
public class TermsOfService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long termsId;

    private boolean isAccepted;

    @OneToMany(mappedBy = "termsOfService")
    private List<UserAgreement> userAgreements = new ArrayList<>();

}
