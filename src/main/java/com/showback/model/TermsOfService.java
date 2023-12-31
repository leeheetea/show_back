package com.showback.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "terms_of_service")
@Getter
@Setter
public class TermsOfService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long termsId;

    private String termCode;
    private String title;

    @OneToMany(mappedBy = "termsOfService")
    @JsonBackReference
    private List<UserAgreement> userAgreements = new ArrayList<>();

}
