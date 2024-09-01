package br.com.tbiazin.repository;

import br.com.tbiazin.domain.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieGenreRepository extends JpaRepository<MovieGenre, Long> {
}
