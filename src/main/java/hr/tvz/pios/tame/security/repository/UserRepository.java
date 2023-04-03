package hr.tvz.pios.tame.security.repository;

import hr.tvz.pios.tame.security.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findByUsername(String username);

    Optional<User> save(User user);

    void delete(String username);

    Optional<User> findById(Long id);

    void follow(String username, String followingUsername);
}
