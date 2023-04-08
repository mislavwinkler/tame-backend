package hr.tvz.pios.tame.post;

import hr.tvz.pios.tame.security.domain.User;
import hr.tvz.pios.tame.security.repository.UserRepository;
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
public class PostRepository implements PostRepositoryInterface{

    private static final String SELECT_ALL = "SELECT * FROM post";

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;
    private final UserRepository userRepository;

    public PostRepository(JdbcTemplate jdbc, UserRepository userRepository) {
        this.jdbc = jdbc;
        this.inserter = new SimpleJdbcInsert(jdbc)
                .withTableName("post")
                .usingGeneratedKeyColumns("id");
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> findAll() {
        return List.copyOf(jdbc.query(SELECT_ALL, this::mapRowToPost));
    }

    @Override
    public List<Post> findAllByMaker(String makerUsername) {
        return List.copyOf(jdbc.query(SELECT_ALL+
                " LEFT JOIN users ON users.id = post.user_id " +
                "WHERE users.username = ?"
                , this::mapRowToPost, makerUsername));
    }

    @Override
    public Optional<Post> findById(Long id) {
        try{
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + " WHERE id = ?", this::mapRowToPost, id)
            );
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Post> save(Post post) {
        try {
            savePostDetails(post);
            return Optional.of(post);
        } catch (DuplicateKeyException e){
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM post WHERE id = ?", id);
    }

    @Override
    public void like(Long postId, String username) {
        jdbc.update("INSERT INTO post_has_likes(post_id, user_id) VALUES (?, ?)", postId, userRepository.findByUsername(username).orElse(null).getId());
    }

    @Override
    public List<Post> findPostsByFollowedUsers(String username) {
        return List.copyOf(jdbc.query(SELECT_ALL+
                " JOIN user_is_following uf ON uf.following_id = post.user_id" +
                " WHERE uf.user_id = ?",
                this::mapRowToPost, userRepository.findByUsername(username).orElse(null).getId()));
    }

    private Post mapRowToPost(ResultSet rs, int rowNum) throws SQLException {
        return new Post(
                rs.getLong("id"),
                rs.getString("text"),
                userRepository.findById(rs.getLong("user_id")).orElse(null),
                rs.getDate("creation_date")
        );
    }

    private String savePostDetails(Post post) {
        Map<String, Object> values = new HashMap<>();

        values.put("text", post.getText());
        values.put("user_id", post.getMaker().getId());
        values.put("creation_date", post.getCreationDate());

        return inserter.executeAndReturnKey(values).toString();
    }
}
