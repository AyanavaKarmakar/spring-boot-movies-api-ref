package software.ayanavakarmakar.movies.controllers;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}