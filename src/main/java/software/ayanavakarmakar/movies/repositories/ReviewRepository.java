package software.ayanavakarmakar.movies.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import software.ayanavakarmakar.movies.models.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {
}
