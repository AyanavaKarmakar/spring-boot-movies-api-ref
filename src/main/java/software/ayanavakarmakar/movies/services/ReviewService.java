package software.ayanavakarmakar.movies.services;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
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

    public Review findSingleReview(ObjectId id) {
        Optional<Review> reviewOptional = reviewRepository.findReviewById(id);

        if (reviewOptional.isPresent()) {
            return reviewOptional.get();
        } else {
            throw new NoSuchElementException("Review with id " + id + " not found!");
        }
    }

    public Review updateReview(ObjectId id, String reviewBody) {
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

    public boolean deleteReview(ObjectId id) {
        Optional<Review> reviewOptional = reviewRepository.findReviewById(id);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();

            // delete the review
            reviewRepository.delete(review);

            // update the associated movie document
            Query movieQuery = new Query(Criteria.where("reviewIds._id").is(id));
            Update movieUpdate = new Update().pull("reviewIds", new BasicDBObject("_id", id));

            mongoTemplate.updateFirst(movieQuery, movieUpdate, Movie.class);
            return true;
        } else {
            throw new NoSuchElementException("Review with id " + id + " not found!");
        }
    }
}
