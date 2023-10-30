package com.showback.mapper;

import com.showback.dto.ReviewDTO;
import com.showback.model.Review;
import com.showback.model.Show;
import com.showback.model.UserAuth;
import com.showback.repository.ShowRepository;
import com.showback.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final UserAuthRepository userAuthRepository;
    private final ShowRepository showRepository;


    public Review toEntity(ReviewDTO reviewDTO){
        if(reviewDTO == null){
            return null;
        }
        Review review = new Review();

        review.setReviewId(reviewDTO.getReviewId());
        review.setReviewGrade(reviewDTO.getReviewGrade());
        review.setReviewText(reviewDTO.getReviewText());
        review.setReviewImgUrl(reviewDTO.getReviewImgUrl());
        if (reviewDTO.getAuthId() != null) {
            UserAuth userAuth = userAuthRepository.findById(reviewDTO.getAuthId()).orElse(null);
            review.setUserAuth(userAuth);
        }
        if (reviewDTO.getShowId() != null) {
            Show show = showRepository.findById(reviewDTO.getShowId()).orElse(null);
            review.setShow(show);
        }
        return review;
    }
}
