package com.showback.model;

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

    private String reviewText;

    private String reviewImgUrl;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}
