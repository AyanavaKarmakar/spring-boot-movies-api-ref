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

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable ObjectId id) {
        try {
            Review review = reviewService.findSingleReview(id);
            if (review != null) {
                return new ResponseEntity<>(review, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> putUpdateReview(@PathVariable ObjectId id, @RequestBody Map<String, String> payload) {
        try {
            Review updatedReview = reviewService.updateReview(id, payload.get("reviewBody"));
            if (updatedReview != null) {
                return new ResponseEntity<>(updatedReview, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable ObjectId id) {
        try {
            boolean isDeleted = reviewService.deleteReview(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
