package hr.tvz.pios.tame.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CommentCommand {

    @NotBlank(message = "Comment must have text")
    private String text;

    @NotNull (message = "Comment must belong to a post")
    private Long postId;

    @NotNull (message = "Comment must belong to a user")
    private String makerUsername;

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Long getPostId() { return postId; }

    public void setPostId(Long postId) { this.postId = postId; }

    public String getMakerUsername() { return makerUsername; }

    public void setMakerUsername(String makerUsername) { this.makerUsername = makerUsername; }

}
