package hr.tvz.pios.tame.comment;

import hr.tvz.pios.tame.post.PostRepository;
import hr.tvz.pios.tame.security.repository.UserRepositoryImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class CommentRepository implements CommentRepositoryInterface{

    private static final String SELECT_ALL = "SELECT * FROM comment";

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;
    private final PostRepository postRepository;
    private final UserRepositoryImpl userRepository;

    public CommentRepository(JdbcTemplate jdbc, PostRepository postRepository, UserRepositoryImpl userRepository) {
        this.jdbc = jdbc;
        this.inserter = new SimpleJdbcInsert(jdbc)
                .withTableName("comment")
                .usingGeneratedKeyColumns("id");
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Comment> findAll()  {
        return List.copyOf(jdbc.query(SELECT_ALL, this::mapRowToComment));
    }

    @Override
    public Optional<Comment> findById(Long id) {
        try{
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + " WHERE id = ?", this::mapRowToComment, id)
            );
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        return List.copyOf(jdbc.query(SELECT_ALL +
                        " LEFT JOIN post ON comment.post_id = post.id " +
                        "WHERE post.id = ?",
                        this::mapRowToComment, postId));
    }

    @Override
    public Optional<Comment> save(Comment comment) {
        try {
            saveCommentDetails(comment);
            return Optional.of(comment);
        } catch (DuplicateKeyException e){
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE comment FROM comment " +
                "WHERE id = ?", id);
    }

    private Comment mapRowToComment(ResultSet rs, int rowNum) throws SQLException {
        return new Comment(
                rs.getLong("id"),
                rs.getString("text"),
                userRepository.findById(rs.getLong("user_id")).get(),
                postRepository.findById(rs.getLong("post_id")).get(),
                rs.getDate("creation_date")
        );
    }
    
    private String saveCommentDetails(Comment comment) {
        Map<String, Object> values = new HashMap<>();

        values.put("text", comment.getText());
        values.put("post_id", comment.getPost().getId());
        values.put("user_id", comment.getMaker().getId());
        values.put("creation_date", comment.getCreationDate());

        return inserter.executeAndReturnKey(values).toString();
    }
}
