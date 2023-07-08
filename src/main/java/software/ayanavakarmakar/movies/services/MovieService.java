package software.ayanavakarmakar.movies.services;

import org.springframework.stereotype.Service;
import software.ayanavakarmakar.movies.models.Movie;
import software.ayanavakarmakar.movies.repositories.MovieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> fetchSingleMovieDetails(String imdbId) {
        return movieRepository.findMovieByImdbId(imdbId);
    }
}
