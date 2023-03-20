package hr.tvz.pios.tame.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.tvz.pios.tame.security.domain.User;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User maker;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "POST_HAS_LIKES",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    @BatchSize(size = 20)
    private Set<User> liked = new HashSet<>();

    public Post() {  }

    public Post(Long id, String text, User maker, Date creationDate) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        this.maker = maker;
    }

    public Post(String text, User maker) {
        this.text = text;
        this.creationDate = java.sql.Date.valueOf(LocalDate.now());
        this.maker = maker;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public Date getCreationDate() {return creationDate;}

    public void setCreationDate(Date creationDate) {this.creationDate = creationDate;}

    public User getMaker() {return maker;}

    public void setMaker(User maker) {this.maker = maker;}

}
