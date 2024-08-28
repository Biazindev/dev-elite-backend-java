package br.com.tbiazin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.tbiazin.domain.Movie;
import br.com.tbiazin.repository.MovieRepository;
import br.com.tbiazin.service.MovieService;

import java.util.List;
import java.util.Optional;

@SpringBootTest(properties = "spring.datasource.url=jdbc:postgresql://localhost:5432/postgres")
@Transactional
class MovieServiceTests {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie();
        testMovie.setTitle("B13");
        testMovie.setTmdbId("12345");
        testMovie.setOverview("the new day");
        testMovie.setRating(8.8);
        testMovie.setThumbnail("https://example.com/thumbnail.jpg");
        testMovie.setBackdropPath("/example_backdrop_path.jpg");
        testMovie.setReleaseDate("2024-01-01");
        testMovie.setPopularity(80.5);
        testMovie.setAdult(false);
        testMovie.setVideo("https://example.com/video.mp4");
        testMovie.setVoteCount(100);
        testMovie.setGenreIds("Action,Drama"); 
    }

    @Test
    void testSaveAndGetFavorite() {
        Movie savedMovie = movieService.saveFavorite(testMovie);

        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getId()).isNotNull();

        Movie foundMovie = movieService.getFavorites().stream()
                .filter(movie -> movie.getId().equals(savedMovie.getId()))
                .findFirst()
                .orElse(null);

        assertThat(foundMovie).isNotNull();
        assertThat(foundMovie.getTitle()).isEqualTo(testMovie.getTitle());
        assertThat(foundMovie.getTmdbId()).isEqualTo(testMovie.getTmdbId());
        assertThat(foundMovie.getOverview()).isEqualTo(testMovie.getOverview());
        assertThat(foundMovie.getRating()).isEqualTo(testMovie.getRating());
    }

    @Test
    void testGetMovieById() {
        Movie savedMovie = movieService.saveFavorite(testMovie);

        Optional<Movie> foundMovieOptional = movieRepository.findById(savedMovie.getId());

        assertThat(foundMovieOptional).isPresent();
        Movie foundMovie = foundMovieOptional.get();
        assertThat(foundMovie.getId()).isEqualTo(savedMovie.getId());
        assertThat(foundMovie.getTitle()).isEqualTo(testMovie.getTitle());
    }

    @Test
    void testUpdateMovie() {
        Movie savedMovie = movieService.saveFavorite(testMovie);

        savedMovie.setTitle("B13 Updated");
        Movie updatedMovie = movieService.saveFavorite(savedMovie);

        assertThat(updatedMovie).isNotNull();
        assertThat(updatedMovie.getTitle()).isEqualTo("B13 Updated");
    }

    @Test
    void testDeleteMovie() {
        Movie savedMovie = movieService.saveFavorite(testMovie);

        movieRepository.deleteById(savedMovie.getId());

        Optional<Movie> foundMovieOptional = movieRepository.findById(savedMovie.getId());
        assertThat(foundMovieOptional).isEmpty();
    }

    @Test
    void testGetAllFavorites() {
        Movie movie1 = new Movie();
        movie1.setTitle("B13");
        movie1.setTmdbId("12345");
        movie1.setOverview("the new day");
        movie1.setRating(8.8);
        movie1.setThumbnail("https://example.com/thumbnail.jpg");
        movie1.setBackdropPath("/example_backdrop_path.jpg");
        movie1.setReleaseDate("2024-01-01");
        movie1.setPopularity(80.5);
        movie1.setAdult(false);
        movie1.setVideo("https://example.com/video.mp4");
        movie1.setVoteCount(100);
        movie1.setGenreIds("Action,Drama"); 

        Movie movie2 = new Movie();
        movie2.setTitle("Saving Private Ryan");
        movie2.setTmdbId("54321");
        movie2.setOverview("onDemand");
        movie2.setRating(8.8);
        movie2.setThumbnail("https://example.com/thumbnail.jpg");
        movie2.setBackdropPath("/example_backdrop_path.jpg");
        movie2.setReleaseDate("2024-01-01");
        movie2.setPopularity(80.5);
        movie2.setAdult(false);
        movie2.setVideo("https://example.com/video.mp4");
        movie2.setVoteCount(100);
        movie2.setGenreIds("Action,Drama"); 

        movieService.saveFavorite(movie1);
        movieService.saveFavorite(movie2);

        List<Movie> favorites = movieService.getFavorites();
        assertThat(favorites).hasSize(2);
        assertThat(favorites).extracting(Movie::getTitle).contains("B13", "Saving Private Ryan");
    }
}
