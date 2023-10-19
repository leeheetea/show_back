package com.showback.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private int reviewGrade;

    private String reviewImgUrl;

    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}
