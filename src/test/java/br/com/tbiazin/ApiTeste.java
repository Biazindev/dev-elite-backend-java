package br.com.tbiazin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import br.com.tbiazin.dto.MovieDTO;
import br.com.tbiazin.repository.IMovieRepository;
import br.com.tbiazin.service.MovieService;

@SpringBootTest
public class ApiTeste {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RestTemplate restTemplate;

    @MockBean
    private IMovieRepository movieRepository;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void testSearchMovies() throws Exception {
        String jsonResponse = "{ \"results\": [ { \"title\": \"Inception\", \"id\": 12345, \"overview\": \"A mind-bending thriller\", \"vote_average\": 8.8 } ] }";
        mockServer.expect(requestTo("https://api.themoviedb.org/3/search/movie?api_key=262ceba5b8f2ec3f703e5666075b2a26&query=Inception&page=1"))
                  .andRespond(withSuccess(jsonResponse, APPLICATION_JSON));

        CompletableFuture<List<MovieDTO>> moviesFuture = movieService.searchMoviesAsync("Inception");
        List<MovieDTO> movies = moviesFuture.join();

        assertThat(movies).hasSize(1);
        assertThat(movies.get(0).getTitle()).isEqualTo("Inception");
        assertThat(movies.get(0).getTmdbId()).isEqualTo("12345");
        assertThat(movies.get(0).getOverview()).isEqualTo("A mind-bending thriller");
        assertThat(movies.get(0).getRating()).isEqualTo(8.8);
    }
}
