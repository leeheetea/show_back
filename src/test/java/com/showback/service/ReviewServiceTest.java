package com.showback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ReviewDTO;
import com.showback.model.Review;
import com.showback.repository.ReviewRepository;
import com.showback.repository.UserAuthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        reviewDTO.setReviewText("test text");
        reviewDTO.setReviewGrade(5);
        reviewDTO.setReviewImgUrl("test url");
        reviewDTO.setShowId(1L);
        Review createdReview = reviewService.createReview(reviewDTO);
    }


}
