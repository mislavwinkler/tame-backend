package hr.tvz.pios.tame.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<CommentDTO> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{postId}")
    public List<CommentDTO> getCommentsByPostId(@PathVariable final Long postId) {
        return commentService.findCommentsByPostId(postId);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable final Long id) {
        return commentService.findById(id).map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }
    @PostMapping
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
    public void delete (@PathVariable Long id){
        commentService.delete(id);
    }
}
