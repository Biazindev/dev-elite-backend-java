package br.com.tbiazin.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private String title;
    private String tmdbId;
    private String overview;
    private Double rating;
    private String thumbnail;
    private String backdropPath;
    private String releaseDate;
    private Double popularity;
    private Boolean adult;
    private Boolean video;
    private Integer voteCount;
    private List<Integer> genreIds;
}
