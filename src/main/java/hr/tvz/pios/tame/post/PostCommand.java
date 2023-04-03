package hr.tvz.pios.tame.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class PostCommand {


    @NotBlank(message = "Post must have text")
    private String text;

    @NotNull(message = "Date od creation is mandatory")
    private Date creationDate;

    @NotBlank(message = "Post must have a maker")
    private String makerUsername;

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public Date getCreationDate() {return creationDate;}

    public void setCreationDate(Date creationDate) {this.creationDate = creationDate;}

    public String getMakerName() {return makerUsername;}

    public void setMakerName(String makerName) {this.makerUsername = makerName;}
}
