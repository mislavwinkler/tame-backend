package hr.tvz.pios.tame.security.repository;

import hr.tvz.pios.tame.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJPA extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
