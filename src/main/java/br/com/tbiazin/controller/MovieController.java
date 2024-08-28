package br.com.tbiazin.controller;

import br.com.tbiazin.dto.MovieDTO;
import br.com.tbiazin.domain.Movie;
import br.com.tbiazin.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public List<MovieDTO> searchMovies(@RequestParam String query) {
        return movieService.searchMovies(query);
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
}
