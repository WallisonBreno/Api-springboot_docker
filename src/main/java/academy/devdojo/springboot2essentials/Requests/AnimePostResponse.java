package academy.devdojo.springboot2essentials.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimePostResponse {
    @NotEmpty(message = "Anime name cannot be empty")
    @NotNull(message = "Anime name cannot be null")
    @Schema(description="Nome Do Anime", required=true)
    private String name;
}
