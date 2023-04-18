package academy.devdojo.springboot2essentials.AnimeController;


import academy.devdojo.springboot2essentials.Requests.AnimePostResponse;
import academy.devdojo.springboot2essentials.Requests.AnimePutRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.utils.DateUtil;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import academy.devdojo.springboot2essentials.Services.AnimeServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
    private final DateUtil dateUtil;
    private final AnimeServices animeServices;
        @GetMapping
        @Operation(summary="Lista todos animes paginados", tags={"anime"})
        public ResponseEntity<Page<Anime>> list( @Parameter(hidden=true) Pageable pageable){
            log.info(dateUtil.formatDateTimeToDatabasetyle(LocalDateTime.now()));
            return new ResponseEntity<>(animeServices.listAll(pageable), HttpStatus.OK);
        }
        
                @GetMapping(path="/all")
        public ResponseEntity<List<Anime>> list(){
            log.info(dateUtil.formatDateTimeToDatabasetyle(LocalDateTime.now()));
            return new ResponseEntity<>(animeServices.listAllNonPageables(), HttpStatus.OK);
        }
    
        @GetMapping(path="/find")
        public ResponseEntity<List<Anime>> findById(@RequestParam String name){
        return new ResponseEntity<>(animeServices.findByName(name), HttpStatus.OK);
    }
        
         @GetMapping(path="/{id}")
        public ResponseEntity<Anime> findByName(@PathVariable Long id){
        return new ResponseEntity<>(animeServices.findById(id), HttpStatus.OK);
    }
        
        
         @GetMapping(path="by-id/{id}")
        public ResponseEntity<Anime> findByIdAuth(@PathVariable Long id, 
                @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(animeServices.findById(id), HttpStatus.OK);
    }
        @PostMapping
        public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostResponse animePostResponse){
       return new ResponseEntity<>(animeServices.save(animePostResponse), HttpStatus.CREATED);
    }
        
        @DeleteMapping(path="/admin/{id}")
        @ApiResponses({
            @ApiResponse(responseCode="204",description="Sucesso"),
            @ApiResponse(responseCode="400",description="Quando o anime não existe")
        })
        public ResponseEntity<Void> delete(@PathVariable Long id){
            animeServices.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        }
        
        @PutMapping
        public ResponseEntity<Anime> replace(@RequestBody AnimePutRequest animePutRequest){
            
            return new ResponseEntity<>(animeServices.replace(animePutRequest), HttpStatus.OK);
            
        }
        
}
