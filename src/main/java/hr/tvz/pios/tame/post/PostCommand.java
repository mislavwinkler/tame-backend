package hr.tvz.pios.tame.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class PostCommand {


    @NotBlank(message = "Post must have text")
    private String text;

    @NotBlank(message = "Post must have a maker")
    private String makerUsername;

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public String getMakerUsername() {return makerUsername;}

    public void setMakerUsername(String makerName) {this.makerUsername = makerName;}
}
