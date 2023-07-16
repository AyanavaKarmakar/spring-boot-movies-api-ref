package software.ayanavakarmakar.movies.controllers;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import software.ayanavakarmakar.movies.models.Review;
import software.ayanavakarmakar.movies.services.ReviewService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {
    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostNewReview() {
        // mock the input payload
        Map<String, String> payload = new HashMap<>();
        payload.put("reviewBody", "Great movie!");
        payload.put("imdbId", "tt1234567");

        // mock the review returned by the service
        Review mockReview = new Review("Great Movie");
        when(reviewService.createReview("Great movie!", "tt1234567"))
                .thenReturn(mockReview);

        // make the request to the endpoint
        ResponseEntity<Review> response = reviewController.postNewReview(payload);

        // verify the service method was called with correct parameters
        verify(reviewService, times(1))
                .createReview("Great movie!", "tt1234567");

        // verify the response status code and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockReview, response.getBody());
    }

    @Test
    public void testGetReviewByID_ReviewNotFound() {
        // mock the input payload
        ObjectId reviewId = new ObjectId("123456789012345678901234");

        // mock the review returned by the service as null to indicate not found
        when(reviewService.findSingleReview(reviewId)).thenReturn(null);

        // make the request to the endpoint
        ResponseEntity<Review> response = reviewController.getReview(reviewId);

        // verify the service method was called with correct parameters
        verify(reviewService, times(1)).findSingleReview(reviewId);

        // verify the response status and code body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetReviewByID_ReviewFound() {
        // mock the input payload
        ObjectId reviewId = new ObjectId("64a9bcb8e85d01724f4b12d8");

        // mock the review returned by the service
        Review mockReview = new Review("Updated review");
        when(reviewService.findSingleReview(reviewId)).thenReturn(mockReview);

        // make the request to the endpoint
        ResponseEntity<Review> response = reviewController.getReview(reviewId);

        // verify the service method was called with correct parameters
        verify(reviewService, times(1)).findSingleReview(reviewId);

        // verify the response status and code body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockReview, response.getBody());
    }

    @Test
    public void testUpdateReview_ReviewNotFound() {
        // mock the input payload
        ObjectId reviewId = new ObjectId("123456789012345678901234");
        Map<String, String> payload = new HashMap<>();
        payload.put("reviewBody", "Updated review");

        // mock the review returned by the service as null to indicate not found
        when(reviewService.updateReview(reviewId, payload.get("reviewBody"))).thenReturn(null);

        // make the request to the endpoint
        ResponseEntity<Review> response = reviewController.putUpdateReview(reviewId, payload);

        // verify the service method was called with correct parameters
        verify(reviewService, times(1)).updateReview(reviewId, payload.get("reviewBody"));

        // verify the response status and code body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateReview_ReviewFound() {
        // mock the input payload
        ObjectId reviewId = new ObjectId("64a9bcb8e85d01724f4b12d8");
        Map<String, String> payload = new HashMap<>();
        payload.put("reviewBody", "Updated review");

        // mock the review returned by the service
        Review mockReview = new Review("Updated review");
        when(reviewService.updateReview(reviewId, payload.get("reviewBody"))).thenReturn(mockReview);

        // make the request to the endpoint
        ResponseEntity<Review> response = reviewController.putUpdateReview(reviewId, payload);

        // verify the service method was called with correct parameters
        verify(reviewService, times(1)).updateReview(reviewId, payload.get("reviewBody"));

        // verify the response status and code body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockReview, response.getBody());
    }
}
