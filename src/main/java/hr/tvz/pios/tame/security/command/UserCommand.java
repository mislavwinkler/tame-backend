package hr.tvz.pios.tame.security.command;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

public class UserCommand {

    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Email is mandatory")
    private String email;

    private String firstname;

    private String lastname;

    private Date dateOfBirth;

    private String profilePicture;

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public Date getDateOfBirth() { return dateOfBirth; }

    public String getProfilePicture() { return profilePicture; }
}
