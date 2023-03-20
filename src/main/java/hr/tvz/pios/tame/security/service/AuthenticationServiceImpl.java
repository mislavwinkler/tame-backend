package hr.tvz.pios.tame.security.service;

import hr.tvz.pios.tame.security.command.LoginCommand;
import hr.tvz.pios.tame.security.command.RegisterCommand;
import hr.tvz.pios.tame.security.domain.User;
import hr.tvz.pios.tame.security.dto.LoginDTO;
import hr.tvz.pios.tame.security.dto.UserDTO;
import hr.tvz.pios.tame.security.repository.UserRepository;
import hr.tvz.pios.tame.security.repository.UserRepositoryImpl;
import hr.tvz.pios.tame.security.repository.UserRepositoryJPA;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserRepositoryJPA userRepositoryJPA;

    public AuthenticationServiceImpl(JwtService jwtService, UserRepositoryImpl userRepository, UserRepositoryJPA userRepositoryJPA) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userRepositoryJPA = userRepositoryJPA;
    }

    @Override
    public List<UserDTO> findAll(){
        return userRepository.findAll().stream().map(this::mapUserToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<LoginDTO> login(LoginCommand command) {
        Optional<User> user = userRepositoryJPA.findByUsername(command.getUsername());

        if (user.isEmpty() || !isMatchingPassword(command.getPassword(), user.get().getPassword())) {
            return Optional.empty();
        }

        return Optional.of(
                new LoginDTO(jwtService.createJwt(user.get()))
        );
    }

    @Override
    public Optional<UserDTO> save(RegisterCommand command) {
        Optional<User> user = userRepository.save(mapRegisterCommandToUser(command));
        if (user.isEmpty() || !isMatchingPassword(command.getPassword(), user.get().getPassword())) {
            return Optional.empty();
        }

        return Optional.of(
                new UserDTO(user.get().getUsername())
        );
        }

    @Override
    public void delete(String username) {
        userRepository.delete(username);
    }

    private User mapRegisterCommandToUser(RegisterCommand registerCommand) {
        return new User(registerCommand.getUsername(), BCrypt.hashpw(registerCommand.getPassword(), BCrypt.gensalt()), registerCommand.getEmail());
    }

    private UserDTO mapUserToDTO(final User user) {
        return new UserDTO(user.getUsername());
    }

    private boolean isMatchingPassword(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}
