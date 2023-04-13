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
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllComments() throws Exception {
        this.mockMvc.perform(
                        get("/comment")
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
    void getCommentsByPostId() throws Exception {
        this.mockMvc.perform(
                        get("/comment/1")
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
    void getCommentById() throws Exception {
        this.mockMvc.perform(
                        get("/comment/id=1")
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
    void saveComment() throws Exception {
        String requestBody = "{\"text\":\"Test\", \"postId\":1, \"makerUsername\":\"admin\"}";
        this.mockMvc.perform(
                        post("/comment")
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

    @Test
    @DirtiesContext
    void deleteComment() throws Exception {
        this.mockMvc.perform(
                        delete("/comment/1")
                                .with(user("admin")
                                        .password("admin")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isNoContent());
    }
}
