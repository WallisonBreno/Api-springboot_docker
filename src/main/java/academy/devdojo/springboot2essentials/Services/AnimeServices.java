
package academy.devdojo.springboot2essentials.Services;

import academy.devdojo.springboot2essentials.Exception.BadRequestException;
import academy.devdojo.springboot2essentials.Mapper.AnimeMapper;
import academy.devdojo.springboot2essentials.domain.Anime;
import java.util.List;
import org.springframework.stereotype.Service;
import academy.devdojo.springboot2essentials.Repository.AnimeRepository;
import academy.devdojo.springboot2essentials.Requests.AnimePostResponse;
import academy.devdojo.springboot2essentials.Requests.AnimePutRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
public class AnimeServices {
    private final AnimeRepository animeRepository;
    
    public Page<Anime> listAll(Pageable pageable){return animeRepository.findAll(pageable);}
    public List<Anime> listAllNonPageables(){return animeRepository.findAll();}
    
    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }
    
        public Anime findById(Long id){
        return animeRepository.findById(id)
                .orElseThrow(()->new BadRequestException("Anime id not found"));
    }
        
    @Transactional
    public Anime save(AnimePostResponse animePostResponse){
        Anime anime=AnimeMapper.INSTANCE.toAnime(animePostResponse);
        anime.setName(animePostResponse.getName());
        return animeRepository.save(anime);
    }

    public void delete(Long id) {
        animeRepository.delete(findById(id));
    }

    public Anime replace(AnimePutRequest animePutRequest) {
        Anime savedAnime = findById(animePutRequest.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequest);
        anime.setId(savedAnime.getId());
        anime.setName(animePutRequest.getName());
        return animeRepository.save(anime);
    }
}
