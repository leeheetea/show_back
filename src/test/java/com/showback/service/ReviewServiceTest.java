package com.showback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ReviewDTO;
import com.showback.dto.UserAuthDTO;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private  UserService userService;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Test
    public void testCreateReview() throws JsonProcessingException {
        Long userId = 1L;
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewText("test text");
        reviewDTO.setReviewGrade(5);
        reviewDTO.setReviewTimestamp(LocalDateTime.now());
        reviewDTO.setShowId(1101L);

        Review createdReview = reviewService.createReview(reviewDTO, userId);
    }

    @Test
    public void testReadReview() throws JsonProcessingException{

        List<ReviewDTO> list =reviewService.findReviewDTOById(1L);
        System.out.println("========================================================================");
        System.out.println(list);
    }

    @Test
    public void testUpdateReview() throws JsonProcessingException{
        ReviewDTO reviewDTO  = new ReviewDTO();
        reviewDTO.setReviewId(2L);
        reviewDTO.setReviewGrade(4);
        reviewDTO.setReviewText("test test good!");
        reviewDTO.setReviewTimestamp(LocalDateTime.now());
        reviewDTO.setShowId(1101L);
        reviewDTO.setShowId(10L);

        reviewService.updateReview(reviewDTO);
        System.out.println("reviewDTO = " + reviewDTO);

    }


}
