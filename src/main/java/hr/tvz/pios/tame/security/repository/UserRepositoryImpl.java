package hr.tvz.pios.tame.security.repository;


import hr.tvz.pios.tame.security.domain.User;
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
public class UserRepositoryImpl implements UserRepository {

    private static final String SELECT_ALL = "SELECT * FROM users";

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;
    private final SimpleJdbcInsert inserterAuthority;

    public UserRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.inserter = new SimpleJdbcInsert(jdbc)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.inserterAuthority = new SimpleJdbcInsert(jdbc)
                .withTableName("user_has_authority");
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(jdbc.query(SELECT_ALL, this::mapRowToUser));
    }

    @Override
    public Optional<User> findById(Long id) {
        try{
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + " WHERE id = ?", this::mapRowToUser, id)
            );
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try{
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + " WHERE username = ?", this::mapRowToUser, username)
            );
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> save(User user) {
        try {
            saveUserDetails(user);
            return Optional.of(user);
        } catch (DuplicateKeyException e){
            return Optional.empty();
        }
    }

    @Override
    public void delete(String username) {
        jdbc.update("DELETE FROM users WHERE username = ?", username);
    }

    @Override
    public void follow(String username, String followingUsername) {
        jdbc.update("INSERT INTO user_is_following(user_id, following_id) VALUES (?, ?)",
                findByUsername(username).orElse(null).getId(),
                findByUsername(followingUsername).orElse(null).getId());
    }

    @Override
    public void unfollow(String username, String followingUsername) {
        jdbc.update("DELETE FROM user_is_following WHERE user_id = ? AND following_id = ?",
                findByUsername(username).orElse(null).getId(),
                findByUsername(followingUsername).orElse(null).getId());
    }

    @Override
    public List<User> findUsersThatUserFollows(String username) {
        return List.copyOf(jdbc.query(SELECT_ALL + " foll" +
                " JOIN user_is_following ON following_id = foll.id" +
                " JOIN users usr ON user_id = usr.id" +
                " WHERE usr.username = ?",
                this::mapRowToUser, username));
    }

    @Override
    public List<User> findUsersThatFollowUser(String username) {
        return List.copyOf(jdbc.query(SELECT_ALL + " usr" +
                " JOIN user_is_following ON user_id = usr.id" +
                " JOIN users foll ON following_id = foll.id" +
                " WHERE foll.username = ?",
                this::mapRowToUser, username));
    }

    @Override
    public List<User> findUserThatLikedPostById(Long id) {
        return List.copyOf(jdbc.query(SELECT_ALL +
                " JOIN post_has_likes uf ON user_id = users.id" +
                " WHERE post_id = ?"
                , this::mapRowToUser, id));
    }

    @Override
    public Optional<User> update(String username, User updatedUser) {
        Long userId;
        try { userId = findByUsername(username).orElse(null).getId(); }
        catch(NullPointerException e) { return Optional.empty(); }

        int executed = jdbc.update("UPDATE users " +
                        "SET users.username = ?, " +
                        "users.email = ?, " +
                        "users.firstname = ?, " +
                        "users.lastname = ?, " +
                        "users.birth_date = ?, " +
                        "users.profile_picture = ? " +
                        "WHERE users.id = ?",
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getFirstname(),
                updatedUser.getLastname(),
                updatedUser.getDateOfBirth(),
                updatedUser.getProfilePicture(),
                userId
        );

        if(executed > 0){
            return Optional.of(updatedUser);
        } else {
            return Optional.empty();
        }
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getDate("birth_date"),
                rs.getDate("registration_date"),
                rs.getString("profile_picture")
        );
    }

    private String saveUserDetails(User user) {
        Map<String, Object> values = new HashMap<>();

        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("firstname", user.getFirstname());
        values.put("lastname", user.getLastname());
        values.put("birth_date", user.getDateOfBirth());
        values.put("registration_date", user.getDateOfRegistration());
        values.put("profile_picture", user.getProfilePicture());

        Number key = inserter.executeAndReturnKey(values);
        inserterAuthority.execute(new HashMap<>(){{put("user_id", key); put("authority_id", 2);}});

        return key.toString();
    }

}
