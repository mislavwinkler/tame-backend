package hr.tvz.pios.tame.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("post")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) { this.postService = postService; }

    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public List<PostDTO> getAllPost(){
        return postService.findAll();
    }

    @GetMapping("/maker={username}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<PostDTO> getAllPostByMaker(@PathVariable final String username){
        return postService.findAllByMaker(username);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<PostDTO> getPostById(@PathVariable final Long id){
        return postService.findById(id).map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }

    @PostMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<PostDTO> save(@Valid @RequestBody final PostCommand quizCommand){
        return postService.save(quizCommand)
                .map(quizDTO -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(quizDTO))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.CONFLICT)
                                .build()
                );
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public void delete (@PathVariable Long id){
        postService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{postId}/{username}")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public void like (@PathVariable Long postId, @PathVariable String username){
        postService.like(postId, username);
    }
}
