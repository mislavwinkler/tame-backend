package hr.tvz.pios.tame.security.service;

import hr.tvz.pios.tame.security.command.LoginCommand;
import hr.tvz.pios.tame.security.command.RegisterCommand;
import hr.tvz.pios.tame.security.domain.User;
import hr.tvz.pios.tame.security.dto.LoginDTO;
import hr.tvz.pios.tame.security.dto.UserDTO;
import hr.tvz.pios.tame.security.repository.UserRepository;
import hr.tvz.pios.tame.security.repository.UserRepositoryImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(JwtService jwtService, UserRepositoryImpl userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> findAll(){
        return userRepository.findAll().stream().map(this::mapUserToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findByUsername(String username){
        return userRepository.findByUsername(username).map(this::mapUserToDTO);
    }

    @Override
    public Optional<LoginDTO> login(LoginCommand command) {
        Optional<User> user = userRepository.findByUsername(command.getUsername());

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
                new UserDTO(
                    user.get().getUsername(),
                    user.get().getEmail(),
                    user.get().getFirstname(),
                    user.get().getLastname(),
                    user.get().getDateOfBirth(),
                    user.get().getDateOfRegistration(),
                    user.get().getProfilePicture()
                )
            );
        }

    @Override
    public void delete(String username) {
        userRepository.delete(username);
    }

    public void follow(String username, String followingUsername) {
        userRepository.follow(username, followingUsername);
    }

    private User mapRegisterCommandToUser(RegisterCommand registerCommand) {
        return new User(
                registerCommand.getUsername(),
                BCrypt.hashpw(registerCommand.getPassword(), BCrypt.gensalt()),
                registerCommand.getEmail(),
                registerCommand.getFirstname(),
                registerCommand.getLastname(),
                registerCommand.getDateOfBirth(),
                new Date(System.currentTimeMillis()),
                registerCommand.getProfilePicture()
        );
    }

    private UserDTO mapUserToDTO(final User user) {
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getDateOfBirth(),
                user.getDateOfRegistration(),
                user.getProfilePicture()
                );
    }

    private boolean isMatchingPassword(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}
