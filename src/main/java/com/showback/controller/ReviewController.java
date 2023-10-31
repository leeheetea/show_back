package com.showback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ReviewDTO;
import com.showback.model.Review;
import com.showback.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO,
                                          HttpServletRequest request,
                                          BindingResult bindingResult) throws JsonProcessingException {
    if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        String token = request.getHeader("Authorization").replace("Bearer ", "");
//        Long userId = Long.parseLong(tokenProvider.validateAndGetUserId(token));
//        Review review = reviewService.createReview(reviewDTO, userId);
//        return ResponseEntity.ok().body(review);
        return null;
    }

}
