package hr.tvz.pios.tame.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("comment")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) { this.commentService = commentService; }

    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public List<CommentDTO> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{postId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<CommentDTO> getCommentsByPostId(@PathVariable final Long postId) {
        return commentService.findCommentsByPostId(postId);
    }

    @GetMapping("/id={id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable final Long id) {
        return commentService.findById(id).map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }
    @PostMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<CommentDTO> save(@Valid @RequestBody final CommentCommand commentCommand){
        return commentService.save(commentCommand)
                .map(CommentDTO -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(CommentDTO))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public void delete (@PathVariable Long id){
        commentService.delete(id);
    }
}
