package hr.tvz.pios.tame.security.controller;

import hr.tvz.pios.tame.security.command.LoginCommand;
import hr.tvz.pios.tame.security.dto.LoginDTO;
import hr.tvz.pios.tame.security.dto.UserDTO;
import hr.tvz.pios.tame.security.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("authentication")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public List<UserDTO> getAllUsers(){
        return authenticationService.findAll();
    }

    @PostMapping("/login")
    public LoginDTO login(@Valid @RequestBody final LoginCommand command) {
        return authenticationService.login(command)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials"));
    }

@ResponseStatus(HttpStatus.NO_CONTENT)
@DeleteMapping("/{username}")
@Secured({"ROLE_ADMIN"})
public void delete (@PathVariable String username){authenticationService.delete(username);
}

}
