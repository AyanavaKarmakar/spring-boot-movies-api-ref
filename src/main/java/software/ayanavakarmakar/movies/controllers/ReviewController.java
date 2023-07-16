package software.ayanavakarmakar.movies.controllers;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.ayanavakarmakar.movies.models.Review;
import software.ayanavakarmakar.movies.services.ReviewService;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> postNewReview(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(reviewService.createReview(
                payload.get("reviewBody"), payload.get("imdbId")), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> putUpdateReview(@PathVariable ObjectId id, @RequestBody Map<String, String> payload) {
        try {
            return new ResponseEntity<>(reviewService.updateReview(
                    id, payload.get("reviewBody")), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
