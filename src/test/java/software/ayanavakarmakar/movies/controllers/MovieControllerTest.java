package software.ayanavakarmakar.movies.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import software.ayanavakarmakar.movies.models.Movie;
import software.ayanavakarmakar.movies.services.MovieService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    public void testGetAllMovies() throws Exception {
        // create a list of movies for the mock response
        Movie movie1 = new Movie();
        movie1.setImdbId("tt3915174");
        movie1.setTitle("Puss in Boots: The Last Wish");
        movie1.setReleaseDate("2022-12-21");
        movie1.setTrailerLink("https://www.youtube.com/watch?v=tHb7WlgyaUc");
        movie1.setGenres(Arrays.asList("Animation", "Action", "Adventure", "Comedy", "Family"));
        movie1.setPoster("https://image.tmdb.org/t/p/w500/1NqwE6LP9IEdOZ57NCT51ftHtWT.jpg");
        movie1.setBackdrops(Arrays.asList(
                "https://image.tmdb.org/t/p/original/r9PkFnRUIthgBp2JZZzD380MWZy.jpg",
                "https://image.tmdb.org/t/p/original/faXT8V80JRhnArTAeYXz0Eutpv9.jpg"
        ));

        Movie movie2 = new Movie();
        movie2.setImdbId("tt1630029");
        movie2.setTitle("Avatar: The Way of Water");
        movie2.setReleaseDate("2022-12-16");
        movie2.setTrailerLink("https://www.youtube.com/watch?v=d9MyW72ELq0");
        movie2.setGenres(Arrays.asList("Science Fiction", "Action", "Adventure"));
        movie2.setPoster("https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg");
        movie2.setBackdrops(Arrays.asList(
                "https://image.tmdb.org/t/p/original/s16H6tpK2utvwDtzZ8Qy4qm5Emw.jpg",
                "https://image.tmdb.org/t/p/original/evaFLqtswezLosllRZtJNMiO1UR.jpg"
        ));

        List<Movie> movies = Arrays.asList(movie1, movie2);

        // mock the movie service to return the list of movies
        when(movieService.allMovies()).thenReturn(movies);

        // perform the GET request to the endpoint
        mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].imdbId").value("tt3915174"))
                .andExpect(jsonPath("$[0].title").value("Puss in Boots: The Last Wish"))
                .andExpect(jsonPath("$[0].releaseDate").value("2022-12-21"))
                .andExpect(jsonPath("$[0].trailerLink").value("https://www.youtube.com/watch?v=tHb7WlgyaUc"))
                .andExpect(jsonPath("$[0].genres").isArray())
                .andExpect(jsonPath("$[0].genres", containsInAnyOrder("Animation", "Action", "Adventure", "Comedy", "Family")))
                .andExpect(jsonPath("$[0].poster").value("https://image.tmdb.org/t/p/w500/1NqwE6LP9IEdOZ57NCT51ftHtWT.jpg"))
                .andExpect(jsonPath("$[0].backdrops").isArray())
                .andExpect(jsonPath("$[0].backdrops", containsInAnyOrder(
                        "https://image.tmdb.org/t/p/original/r9PkFnRUIthgBp2JZZzD380MWZy.jpg",
                        "https://image.tmdb.org/t/p/original/faXT8V80JRhnArTAeYXz0Eutpv9.jpg"
                )))

                .andExpect(jsonPath("$[1].imdbId").value("tt1630029"))
                .andExpect(jsonPath("$[1].title").value("Avatar: The Way of Water"))
                .andExpect(jsonPath("$[1].releaseDate").value("2022-12-16"))
                .andExpect(jsonPath("$[1].trailerLink").value("https://www.youtube.com/watch?v=d9MyW72ELq0"))
                .andExpect(jsonPath("$[1].genres").isArray())
                .andExpect(jsonPath("$[1].genres", containsInAnyOrder("Science Fiction", "Action", "Adventure")))
                .andExpect(jsonPath("$[1].poster").value("https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"))
                .andExpect(jsonPath("$[1].backdrops").isArray())
                .andExpect(jsonPath("$[1].backdrops", containsInAnyOrder(
                        "https://image.tmdb.org/t/p/original/s16H6tpK2utvwDtzZ8Qy4qm5Emw.jpg",
                        "https://image.tmdb.org/t/p/original/evaFLqtswezLosllRZtJNMiO1UR.jpg"
                )));
    }
}
