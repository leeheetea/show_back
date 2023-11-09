package com.showback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.showback.dto.ReviewDTO;
import com.showback.model.Review;
import com.showback.security.TokenProvider;
import com.showback.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            Long userId = Long.parseLong(tokenProvider.validateAndGetUserId(token));

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
//        if (reviewDTOList == null || reviewDTOList.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("리뷰를 찾을 수 없음");
//        }
        return ResponseEntity.ok().body(reviewDTOList);
    }

    @GetMapping("/one/{reviewId}")
    public ResponseEntity<ReviewDTO> readOndReview(@PathVariable("reviewId") Long reviewId) throws JsonProcessingException {

        ReviewDTO reviewDTO = reviewService.findById(reviewId);
        return ResponseEntity.ok().body(reviewDTO);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@RequestBody ReviewDTO reviewDTO) throws JsonProcessingException {
        boolean result = reviewService.updateReview(reviewDTO);
        if (result){
            return ResponseEntity.ok().body("review update");
        }
        return ResponseEntity.ok().body("err");
    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Long reviewId) throws JsonProcessingException {
        boolean result = reviewService.deleteReview(reviewId);
        if (result) {
            return ResponseEntity.ok().body("리뷰가 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("리뷰 삭제 중 오류가 발생했습니다.");
        }
    }

}
