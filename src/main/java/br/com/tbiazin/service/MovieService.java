package br.com.tbiazin.service;

import br.com.tbiazin.domain.Movie;
import br.com.tbiazin.dto.MovieDTO;
import br.com.tbiazin.repository.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final IMovieRepository movieRepository;
    private final RestTemplate restTemplate;
    private final String tmdbApiKey = "262ceba5b8f2ec3f703e5666075b2a26";
    private final String tmdbApiUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + tmdbApiKey + "&query=";
    private final String topRatedApiUrl = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + tmdbApiKey;
    
    @Autowired
    public MovieService(IMovieRepository movieRepository, RestTemplate restTemplate) {
        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<List<MovieDTO>> searchMoviesAsync(String query) {
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

        return CompletableFuture.completedFuture(allMovies);
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
    
    public void deleteMovieByTmdbId(String tmdbId) {
        Movie movie = movieRepository.findByTmdbId(tmdbId);
        if (movie != null) {
            movieRepository.delete(movie);
        }
    }

    public String shareFavoritesLink() {
        List<Movie> favorites = movieRepository.findAll();
        return "https://myapp.com/share?movies=" +
                favorites.stream()
                        .map(Movie::getTmdbId)
                        .collect(Collectors.joining(","));
    }

    public void deleteMovie(Long id) {
        Optional<Movie> movieOpt = movieRepository.findById(id);
        if (movieOpt.isPresent()) {
            movieRepository.delete(movieOpt.get());
        }
    }
    
    public CompletableFuture<List<MovieDTO>> searchMoviesByNameAsync(String query) {
        return CompletableFuture.supplyAsync(() -> {
            List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(query);
            return movies.stream().map(this::convertToDTO).collect(Collectors.toList());
        });
    }

    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setTmdbId(movie.getTmdbId());
        movieDTO.setOverview(movie.getOverview());
        movieDTO.setRating(movie.getRating());
        movieDTO.setThumbnail(movie.getThumbnail());
        movieDTO.setBackdropPath(movie.getBackdropPath());
        movieDTO.setReleaseDate(movie.getReleaseDate());
        movieDTO.setPopularity(movie.getPopularity());
        movieDTO.setAdult(movie.getAdult());
        movieDTO.setVideo(movie.getVideo());
        movieDTO.setVoteCount(movie.getVoteCount());
        movieDTO.setGenreIds(movie.getGenreIds());
        return movieDTO;
    }

    @Async
    public CompletableFuture<List<MovieDTO>> searchTopRatedMoviesAsync() {
        List<MovieDTO> allMovies = new ArrayList<>();
        int currentPage = 1;
        int totalPages = 1;

        while (currentPage <= totalPages) {
            String url = topRatedApiUrl + "&page=" + currentPage;
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

        return CompletableFuture.completedFuture(allMovies);
    }

    @GetMapping("/details/{tmdbId}")
    public CompletableFuture<MovieDTO> getMovieDetailsAsync(String tmdbId) {
        String url = "https://api.themoviedb.org/3/movie/" + tmdbId + "?api_key=" + tmdbApiKey;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        MovieDTO movieDTO = new MovieDTO();
        if (response != null) {
            movieDTO.setTitle((String) response.get("original_title"));
            movieDTO.setTmdbId(response.get("id").toString());
            movieDTO.setOverview((String) response.get("overview"));
            movieDTO.setRating(Double.parseDouble(response.get("vote_average").toString()));
            movieDTO.setThumbnail("https://image.tmdb.org/t/p/w500" + response.get("poster_path"));
            movieDTO.setBackdropPath("https://image.tmdb.org/t/p/w500" + response.get("backdrop_path"));
            movieDTO.setReleaseDate((String) response.get("release_date"));
            movieDTO.setPopularity(Double.parseDouble(response.get("popularity").toString()));
            movieDTO.setAdult((Boolean) response.get("adult"));
            movieDTO.setVideo((Boolean) response.get("video"));
            movieDTO.setVoteCount(Integer.parseInt(response.get("vote_count").toString()));
            movieDTO.setGenreIds((List<Integer>) response.get("genre_ids"));
        }

        return CompletableFuture.completedFuture(movieDTO);
    }
}
