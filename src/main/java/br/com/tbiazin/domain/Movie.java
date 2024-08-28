package br.com.tbiazin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TB_MOVIE")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "tmdb_id", nullable = false, unique = true)
    private String tmdbId;

    @Column(name = "overview", nullable = true)
    private String overview;

    @Column(name = "rating", nullable = true)
    private Double rating;

    @Column(name = "thumbnail", nullable = true)
    private String thumbnail;

    @Column(name = "backdrop_path", nullable = true)
    private String backdropPath;

    @Column(name = "release_date", nullable = true)
    private String releaseDate;

    @Column(name = "popularity", nullable = true)
    private Double popularity;

    @Column(name = "adult", nullable = true)
    private Boolean adult;

    @Column(name = "video", nullable = true)
    private String video;

    @Column(name = "vote_count", nullable = true)
    private Integer voteCount;

    @Column(name = "genre_ids", nullable = true)
    private String genreIds;
}
