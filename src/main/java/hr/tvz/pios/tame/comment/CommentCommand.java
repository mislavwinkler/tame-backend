package hr.tvz.pios.tame.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CommentCommand {

    @NotNull(message = "Comment must have an ID")
    private Long id;

    @NotBlank(message = "Comment must have text")
    private String text;

    @NotNull (message = "Comment must belong to a post")
    private Long postId;

    @NotNull (message = "Answer must belong to a user")
    private String makerUsername;

    @NotNull(message = "Date od creation is mandatory")
    private Date creationDate;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Long getPostId() { return postId; }

    public void setPostId(Long postId) { this.postId = postId; }

    public String getMakerUsername() { return makerUsername; }

    public void setMakerUsername(String makerUsername) { this.makerUsername = makerUsername; }

    public Date getCreationDate() { return creationDate; }

    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
}
