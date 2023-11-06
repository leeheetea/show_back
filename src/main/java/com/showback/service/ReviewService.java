package com.showback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ReviewDTO;
import com.showback.mapper.ReviewMapper;
import com.showback.model.Review;
import com.showback.model.UserAuth;
import com.showback.repository.ReviewRepository;
import com.showback.repository.ShowRepository;
import com.showback.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserAuthRepository userAuthRepository;
    private final ReviewMapper reviewMapper;

    public List<ReviewDTO> findReviewDTOById(Long showId) {
        List<Review> reviews = reviewRepository.findAllByShowId(showId);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for(Review review : reviews){
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setReviewId(review.getReviewId());
            reviewDTO.setReviewGrade(review.getReviewGrade());
            reviewDTO.setReviewText(review.getReviewText());
            reviewDTO.setReviewTimestamp(review.getReviewTimestamp());
            // Check if show is not null
            if (review.getShow() != null) {
                reviewDTO.setShowId(review.getShow().getShowId());
            }
            // Check if userAuth is not null
            if (review.getUserAuth() != null) {
                reviewDTO.setAuthId(review.getUserAuth().getAuthId());
            }
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }


    @Transactional
    public Review createReview(ReviewDTO reviewDTO, Long userId) throws JsonProcessingException{

        Review review = reviewMapper.toEntity(reviewDTO);
        UserAuth UserAuth = userAuthRepository.findByUserId(userId);
        review.setUserAuth(UserAuth);

        return reviewRepository.save(review);
    }

    public Long updateReview(ReviewDTO reviewDTO) throws  JsonProcessingException{
        Review review = reviewRepository.findById(reviewDTO.getReviewId())
                .orElseThrow();
        review.setReviewText(reviewDTO.getReviewText());
        review.setReviewGrade(reviewDTO.getReviewGrade());
        review.setReviewTimestamp(reviewDTO.getReviewTimestamp());

        return review.getReviewId();

    }

    public boolean deleteReview(Review review) throws  JsonProcessingException{
        try {
            reviewRepository.delete(review);
            return true;
        } catch (Exception e) {
            System.out.println( "리뷰 삭제 실패: " + e.getMessage());
            return false;
        }
    }
}