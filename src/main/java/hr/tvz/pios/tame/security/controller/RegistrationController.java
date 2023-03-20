package hr.tvz.pios.tame.security.controller;

import hr.tvz.pios.tame.security.command.RegisterCommand;
import hr.tvz.pios.tame.security.dto.UserDTO;
import hr.tvz.pios.tame.security.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("registration")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

    private final AuthenticationService authenticationService;

    public RegistrationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> register(@Valid @RequestBody final RegisterCommand command) {
        return authenticationService.save(command)
                .map(userDTO -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(userDTO))
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.CONFLICT)
                                .build()
                );
    }

}
