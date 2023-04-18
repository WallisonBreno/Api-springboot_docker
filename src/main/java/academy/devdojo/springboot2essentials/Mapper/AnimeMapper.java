package academy.devdojo.springboot2essentials.Mapper;

import academy.devdojo.springboot2essentials.Requests.AnimePostResponse;
import academy.devdojo.springboot2essentials.Requests.AnimePutRequest;
import academy.devdojo.springboot2essentials.domain.Anime;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
    public abstract Anime toAnime(AnimePostResponse animePostResponse);
    public abstract Anime toAnime(AnimePutRequest animePutRequest);
}
