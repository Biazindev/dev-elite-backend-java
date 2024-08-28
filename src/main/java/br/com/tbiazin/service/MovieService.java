package br.com.tbiazin.service;

import br.com.tbiazin.domain.Movie;
import br.com.tbiazin.dto.MovieDTO;
import br.com.tbiazin.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate;
    private final String tmdbApiKey = "262ceba5b8f2ec3f703e5666075b2a26";
    private final String tmdbApiUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + tmdbApiKey + "&query=";

    @Autowired
    public MovieService(MovieRepository movieRepository, RestTemplate restTemplate) {
        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
    }

    public List<MovieDTO> searchMovies(String query) {
        List<MovieDTO> allMovies = new ArrayList<>();
        int currentPage = 1;
        int totalPages = 1;
        
        while (currentPage <= totalPages) {
            String url = tmdbApiUrl + query + "&page=" + currentPage;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response != null) {
                totalPages = ((Number) response.get("total_pages")).intValue();
                List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
                
                String imageBaseUrl = "https://image.tmdb.org/t/p/w500";
                
                List<MovieDTO> movies = results.stream().map(result -> {
                    MovieDTO movieDTO = new MovieDTO();
                    movieDTO.setTitle((String) result.get("original_title"));
                    movieDTO.setTmdbId(result.get("id").toString());
                    movieDTO.setOverview((String) result.get("overview"));
                    movieDTO.setRating(Double.parseDouble(result.get("vote_average").toString()));
                    movieDTO.setThumbnail(imageBaseUrl + result.get("poster_path"));
                    movieDTO.setBackdropPath(imageBaseUrl + (String) result.get("backdrop_path"));
                    movieDTO.setReleaseDate((String) result.get("release_date"));
                    movieDTO.setPopularity(Double.parseDouble(result.get("popularity").toString()));
                    movieDTO.setAdult((Boolean) result.get("adult"));
                    movieDTO.setVideo((Boolean) result.get("video"));
                    movieDTO.setVoteCount(Integer.parseInt(result.get("vote_count").toString()));
                    movieDTO.setGenreIds((List<Integer>) result.get("genre_ids"));

                    return movieDTO;
                }).collect(Collectors.toList());

                allMovies.addAll(movies);
                currentPage++;
            } else {
                break;
            }
        }
        
        return allMovies;
    }

    public Movie saveFavorite(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getFavorites() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie updateMovie(Long id, Movie movie) {
        if (movieRepository.existsById(id)) {
            movie.setId(id);
            return movieRepository.save(movie);
        }
        return null;
    }

    public void deleteMovie(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        }
    }

    public String shareFavoritesLink() {
        List<Movie> favorites = movieRepository.findAll();
        return "https://myapp.com/share?movies=" +
                favorites.stream()
                        .map(Movie::getTmdbId)
                        .collect(Collectors.joining(","));
    }
}
