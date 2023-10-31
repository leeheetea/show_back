package com.showback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private int reviewGrade;

    @Column(columnDefinition = "TEXT")
    private String reviewText;

    private String reviewImgUrl;

    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonBackReference
    private Show show;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    @JsonBackReference
    private UserAuth userAuth;
}
