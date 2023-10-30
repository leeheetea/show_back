package com.showback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ReviewDTO;
import com.showback.model.Review;
import com.showback.repository.ReviewRepository;
import com.showback.repository.UserAuthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Test
    public void testCreateReview() throws JsonProcessingException {
        ReviewDTO reviewDTO = new ReviewDTO();
//        reviewDTO.setAuthId(1L);
        reviewDTO.setReviewText("test text");
        reviewDTO.setReviewGrade(5);
        reviewDTO.setReviewImgUrl("test url");
        reviewDTO.setShowId(1L);
        Review createdReview = reviewService.createReview(reviewDTO);


    }
}
