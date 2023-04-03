package hr.tvz.pios.tame.post;

import java.util.Date;

public class PostDTO {

    private Long id;
    private String text;

    private Date creationDate;

    private String makerUsername;

    public PostDTO(Long id, String text, Date creationDate, String makerUsername) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        this.makerUsername = makerUsername;
    }

    public Long getId() { return id; }

    public String getText() { return text; }

    public Date getCreationDate() { return creationDate; }

    public String getMakerUsername() { return makerUsername; }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", makerUsername=" + makerUsername +
                '}';
    }
}
