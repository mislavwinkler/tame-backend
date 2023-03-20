package hr.tvz.pios.tame.security.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterCommand {

    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, message = "Passwords must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email is mandatory")
    private String email;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {return email;}
}
