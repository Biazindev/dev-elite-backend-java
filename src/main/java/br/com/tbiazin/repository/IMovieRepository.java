package br.com.tbiazin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tbiazin.domain.Movie;

import java.util.List;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContaining(String title);

	void deleteByTmdbId(Long id);

	boolean existsByTmdbId(Long id);

	Movie findByTmdbId(String tmdbId);

	Movie findByTmdbId(Long tmdbId);

	List<Movie> findByTitleContainingIgnoreCase(String query);
}
