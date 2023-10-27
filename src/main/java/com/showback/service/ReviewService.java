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

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ShowRepository showRepository;
    private final UserAuthRepository userAuthRepository;
    private  final ReviewMapper reviewMapper;

    @Transactional
    public Review createReview(ReviewDTO reviewDTO) throws JsonProcessingException{
        UserAuth userAuth = null;
        if(reviewDTO.getAuthId() != null){
            userAuth = userAuthRepository.findById(reviewDTO.getAuthId())
                    .orElseThrow(EntityNotFoundException::new);
        }
        Review review = reviewMapper.toEntity(reviewDTO);

        return reviewRepository.save(review);
    }
}
