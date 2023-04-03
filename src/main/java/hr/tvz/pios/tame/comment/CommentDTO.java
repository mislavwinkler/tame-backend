package hr.tvz.pios.tame.comment;

public class CommentDTO {

    private final Long id;
    private final String text;
    private final Long postId;
    private final String makerUsername;

    public CommentDTO(Long id, String text, Long postId, String makerUsername) {
        this.id = id;
        this.text = text;
        this.postId = postId;
        this.makerUsername = makerUsername;
    }

    public Long getId() { return id; }

    public String getText() { return text; }

    public Long getPostId() { return postId; }

    public String getMakerUsername() { return makerUsername; }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id='" + id + '\'' +
                ", text=" + text +
                ", postId=" + postId +'\'' +
                ", getMakerUsername=" + makerUsername +'\'' +
                '}';
    }
}
