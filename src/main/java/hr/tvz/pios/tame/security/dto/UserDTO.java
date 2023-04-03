package hr.tvz.pios.tame.security.dto;

import javax.persistence.Column;
import java.sql.Date;

public class UserDTO {
    private String username;

    private String email;

    private String firstname;

    private String lastname;

    private Date dateOfBirth;

    private Date dateOfRegistration;

    private String profilePicture;

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO(String username, String email, String firstname, String lastname, Date dateOfBirth, Date dateOfRegistration, String profilePicture) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistration = dateOfRegistration;
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() { return email; }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public Date getDateOfBirth() { return dateOfBirth; }

    public Date getDateOfRegistration() { return dateOfRegistration; }

    public String getProfilePicture() { return profilePicture; }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", dateOfRegistration='" + dateOfRegistration + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }
}
