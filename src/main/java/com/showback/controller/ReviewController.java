package com.showback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ReviewDTO;
import com.showback.model.Review;
import com.showback.security.TokenProvider;
import com.showback.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO,
                                          HttpServletRequest request,
                                          BindingResult bindingResult) throws JsonProcessingException {

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            System.out.println("--token = " + token);
            Long userId = Long.parseLong(tokenProvider.validateAndGetUserId(token));
            System.out.println("--userId = " + userId);

            Review review = reviewService.createReview(reviewDTO, userId);
            if (review != null){
                return ResponseEntity.ok().body(review);
            }else {
                return ResponseEntity.badRequest().body("createReview fail");
            }
    }

    @GetMapping("/{showId}")
    public ResponseEntity<List<ReviewDTO>> ReadReview(@PathVariable("showId") Long showId){
        List<ReviewDTO> reviewDTOList = reviewService.findReviewDTOById(showId);
        return ResponseEntity.ok().body(reviewDTOList);
    }


    @PutMapping("/{reviewId}")
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO,
                                          HttpServletRequest request,
                                          @PathVariable("reviewId") Long reviewId) throws JsonProcessingException {
        reviewService.updateReview(reviewDTO);
        return ResponseEntity.ok().body(reviewId);
    }

}
