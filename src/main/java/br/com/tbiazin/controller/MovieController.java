package br.com.tbiazin.controller;

import br.com.tbiazin.dto.MovieDTO;
import br.com.tbiazin.domain.Movie;
import br.com.tbiazin.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public CompletableFuture<List<MovieDTO>> searchMovies(@RequestParam String query) {
        return movieService.searchMoviesAsync(query);
    }
    
    @DeleteMapping("/tmdb/{tmdbId}")
    public ResponseEntity<Void> deleteMovieByTmdbId(@PathVariable String tmdbId) {
        movieService.deleteMovieByTmdbId(tmdbId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/favorites")
    public Movie saveFavorite(@RequestBody Movie movie) {
        return movieService.saveFavorite(movie);
    }

    @GetMapping("/favorites")
    public List<Movie> getFavorites() {
        return movieService.getFavorites();
    }

    @GetMapping("/share")
    public String shareFavoritesLink() {
        return movieService.shareFavoritesLink();
    }
    
    @GetMapping("/top-rated")
    public CompletableFuture<List<MovieDTO>> getTopRatedMoviesAsync() {
        return movieService.searchTopRatedMoviesAsync();
    }
    
    @GetMapping("/details/{tmdbId}")
    public CompletableFuture<MovieDTO> getMovieDetails(@PathVariable String tmdbId) {
        return movieService.getMovieDetailsAsync(tmdbId);
    }

}
