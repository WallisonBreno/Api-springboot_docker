package academy.devdojo.springboot2essentials.Requests;

import academy.devdojo.springboot2essentials.domain.Anime;
import lombok.Data;

@Data
public class AnimePutRequest {    
    private String name;
    private Long Id;
}
