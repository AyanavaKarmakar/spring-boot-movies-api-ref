package software.ayanavakarmakar.movies.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import software.ayanavakarmakar.movies.models.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {
}