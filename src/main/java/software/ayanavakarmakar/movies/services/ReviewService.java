package software.ayanavakarmakar.movies.services;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import software.ayanavakarmakar.movies.models.Movie;
import software.ayanavakarmakar.movies.models.Review;
import software.ayanavakarmakar.movies.repositories.ReviewRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MongoTemplate mongoTemplate;

    public ReviewService(ReviewRepository reviewRepository, MongoTemplate mongoTemplate) {
        this.reviewRepository = reviewRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Review createReview(String reviewBody, String imdbId) {
        Review review = reviewRepository.insert(new Review(reviewBody));

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }

    public Review updateReview(String id, String reviewBody) {
        Optional<Review> reviewOptional = reviewRepository.findReviewById(id);

        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            review.setBody(reviewBody);
            reviewRepository.save(review);

            // update the associated movie document
            Query movieQuery = new Query(Criteria.where("reviewIds._id").is(id));
            Update movieUpdate = new Update().set("reviewIds.$.body", reviewBody);

            mongoTemplate.updateFirst(movieQuery, movieUpdate, Movie.class);

            return review;
        } else {
            throw new NoSuchElementException("Review with id " + id + " not found!");
        }
    }
}
