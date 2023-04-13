package hr.tvz.pios.tame;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllUsers() throws Exception {
        this.mockMvc.perform(
                        get("/authentication")
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    void getUserByUsername() throws Exception {
        this.mockMvc.perform(
                        get("/authentication/admin")
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.username").value("admin"));

    }

    @Test
    @DirtiesContext
    void login() throws Exception {
        String requestBody = "{\"username\":\"admin\", \"password\":\"admin\"}";
        this.mockMvc.perform(
                        post("/authentication/login")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    @DirtiesContext
    void deleteUser() throws Exception {
        this.mockMvc.perform(
                        delete("/authentication/test")
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DirtiesContext
    void follow() throws Exception {
        this.mockMvc.perform(
                        put("/authentication/test/admin")
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    void unfollow() throws Exception {
        this.mockMvc.perform(
                        delete("/authentication/test/admin")
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isOk());
    }

    @Test
    void getUsersThatUserFollows() throws Exception {
        this.mockMvc.perform(
                        get("/authentication/follows/admin")
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    void getUsersThatFollowUser() throws Exception {
        this.mockMvc.perform(
                        get("/authentication/following/admin")
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    @DirtiesContext
    void updateUser() throws Exception {
        String requestBody = "{\"username\":\"Test\", \"email\":\"test@tvz.hr\", \"firstname\":\"Ime\", \"lastname\":\"Prezime\", \"dateOfBirth\": null, \"profilePicture\": null}";
        this.mockMvc.perform(
                        put("/authentication/update/test")
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}
