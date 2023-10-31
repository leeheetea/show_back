package com.showback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ReviewDTO;
import com.showback.mapper.ReviewMapper;
import com.showback.model.Review;
import com.showback.model.Show;
import com.showback.model.UserAuth;
import com.showback.repository.ReviewRepository;
import com.showback.repository.ShowRepository;
import com.showback.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ShowRepository showRepository;
    private final UserAuthRepository userAuthRepository;
    private  final ReviewMapper reviewMapper;

    @Transactional
    public Review createReview(ReviewDTO reviewDTO, Long userId) throws JsonProcessingException{
        UserAuth userAuth = null;

        Review review = reviewMapper.toEntity(reviewDTO);

        return reviewRepository.save(review);
    }

}
