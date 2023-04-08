package hr.tvz.pios.tame.security.service;

import hr.tvz.pios.tame.security.command.LoginCommand;
import hr.tvz.pios.tame.security.command.RegisterCommand;
import hr.tvz.pios.tame.security.command.UserCommand;
import hr.tvz.pios.tame.security.domain.User;
import hr.tvz.pios.tame.security.dto.LoginDTO;
import hr.tvz.pios.tame.security.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface AuthenticationService {

    List<UserDTO> findAll();

    Optional<UserDTO> findByUsername(String username);

    Optional<LoginDTO> login(LoginCommand command);

    Optional<UserDTO> save(RegisterCommand command);

    void delete(String username);

    void follow(String username, String followingUsername);

    List<UserDTO> findUsersThatUserFollows(String username);

    List<UserDTO> findUsersThatFollowUser(String username);

    List<UserDTO> findUserThatLikedPostById(Long id);

    Optional<UserDTO> update(Long id, UserCommand userCommand);
}
