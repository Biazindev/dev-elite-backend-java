package br.com.tbiazin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tbiazin.domain.Movie;
import br.com.tbiazin.service.MovieService;

@SpringBootTest
class MoviesListApplicationTests {

    @Autowired
    private MovieService movieService;

    @Test
    void contextLoads() {
        assertThat(movieService).isNotNull();
       
        Movie movie = new Movie();
        movie.setTitle("B13");
        movie.setTmdbId("12345");
        movie.setOverview("the new day");
        movie.setRating(8.8);
        movie.setThumbnail("https://example.com/thumbnail.jpg");
        movie.setBackdropPath("/example_backdrop_path.jpg");
        movie.setReleaseDate("2024-01-01");
        movie.setPopularity(80.5);
        movie.setAdult(false);
        movie.setVideo("https://example.com/video.mp4");
        movie.setVoteCount(100);

        Map<String, Integer> genreMap = new HashMap<>();
        genreMap.put("Action", 28);
        genreMap.put("Drama", 18);

        String genreString = "Action,Drama";
        List<Integer> genreIds = Arrays.stream(genreString.split(","))
                .map(String::trim)
                .map(genreMap::get)
                .collect(Collectors.toList());

        movie.setGenreIds(genreIds); 

        movieService.saveFavorite(movie);

        assertThat(movieService.getFavorites()).isNotEmpty();
        assertThat(movieService.getFavorites()).contains(movie);

        Movie savedMovie = movieService.getFavorites().stream()
            .filter(m -> m.getTmdbId().equals("12345"))
            .findFirst()
            .orElse(null);

        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getTitle()).isEqualTo("B13");
        assertThat(savedMovie.getTmdbId()).isEqualTo("52616261");
        assertThat(savedMovie.getOverview()).isEqualTo("the new day");
        assertThat(savedMovie.getRating()).isEqualTo(8.8);
        assertThat(savedMovie.getThumbnail()).isEqualTo("https://example.com/thumbnail.jpg");
        assertThat(savedMovie.getBackdropPath()).isEqualTo("/example_backdrop_path.jpg");
        assertThat(savedMovie.getReleaseDate()).isEqualTo("2024-01-01");
        assertThat(savedMovie.getPopularity()).isEqualTo(80.5);
        assertThat(savedMovie.getAdult()).isFalse();
        assertThat(savedMovie.getVideo()).isEqualTo("https://example.com/video.mp4");
        assertThat(savedMovie.getVoteCount()).isEqualTo(100);
        assertThat(savedMovie.getIsFavorite()).isTrue();
        assertThat(savedMovie.getGenreIds()).isEqualTo(genreIds);
    }
    
    public void DeleteMovieTeste() {
		Movie savedMovie = movieService.saveFavorite(testMovie);

        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getId()).isNotNull();

        movieService.deleteMovie(savedMovie.getId());

		Optional<Movie> foundMovieOptional = movieRepository.findById(savedMovie.getId());
        assertThat(foundMovieOptional).isEmpty();
    }
}
