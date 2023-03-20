package hr.tvz.pios.tame.security.dto;

public class UserDTO {
    private String username;

    public UserDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                '}';
    }
}
