package com.showback.mapper;

import com.showback.dto.ReviewDTO;
import com.showback.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    public Review toEntity(ReviewDTO reviewDTO){

        if(reviewDTO == null){
            return null;
        }

        Review review = new Review();
        review.setReviewId(reviewDTO.getReviewId());
        review.setReviewGrade(reviewDTO.getReviewGrade());
        review.setReviewText(reviewDTO.getReviewText());
        review.setReviewImgUrl(reviewDTO.getReviewImgUrl());
//        review.setUserAuth();// 테이블에서 찾기 db에서 꺼내와서 넣기!!!!! 
//        review.setShow();

        return review;
    }
}
