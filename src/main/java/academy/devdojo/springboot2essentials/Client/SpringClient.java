package academy.devdojo.springboot2essentials.Client;

import academy.devdojo.springboot2essentials.domain.Anime;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
       ResponseEntity<Anime> entity;
        entity = new RestTemplate().getForEntity("http://localhost:8080/animes/1", Anime.class);
       log.info(entity);
       
       Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
       log.info(Arrays.toString(animes));
       
       ResponseEntity<List<Anime>> exchange;
        exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET,null,
        new ParameterizedTypeReference<List<Anime>>(){});
        log.info(exchange.getBody());
        
        //Anime bleach = Anime.builder().name("Bleach").build();
        //Anime bleachResponse = new RestTemplate().postForObject("http://localhost:8080/animes", bleach, Anime.class);
       // log.info("Anime Saved {}", bleachResponse);
        
        Anime evangelion = Anime.builder().name("Evangelion").build();
        ResponseEntity<Anime> evangelionResponse;
        evangelionResponse = new RestTemplate().exchange("http://localhost:8080/animes",HttpMethod.POST,
        new HttpEntity<>(evangelion), Anime.class);
        log.info(evangelionResponse);
        
        Anime animeToBeUpdated = evangelionResponse.getBody();
        animeToBeUpdated.setName("Evangelion Filme");
        ResponseEntity<Anime> evangelionUpdated;
        evangelionUpdated = new RestTemplate().exchange("http://localhost:8080/animes",HttpMethod.PUT,
        new HttpEntity<>(animeToBeUpdated), Anime.class);
        log.info(evangelionUpdated);
        
        ResponseEntity<Void> evangelionDeleted;
        evangelionDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{Id}",HttpMethod.DELETE,
        null, Void.class, animeToBeUpdated.getId());
        log.info(evangelionDeleted);
    }
}
