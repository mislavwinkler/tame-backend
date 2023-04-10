package hr.tvz.pios.tame.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.tvz.pios.tame.post.Post;
import hr.tvz.pios.tame.security.domain.User;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User maker;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @Column(name = "creation_date")
    private Date creationDate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "POST_HAS_LIKES",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    @BatchSize(size = 20)
    private Set<User> liked = new HashSet<>();

    public Comment(String text, User maker, Post post, Date creationDate) {
        this.text = text;
        this.maker = maker;
        this.post = post;
        this.creationDate = creationDate;
    }

    public Comment(Long id, String text, User maker, Post post, Date creationDate) {
        this.id = id;
        this.text = text;
        this.maker = maker;
        this.post = post;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getMaker() {
        return maker;
    }

    public void setMaker(User maker) {
        this.maker = maker;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<User> getLiked() {
        return liked;
    }

    public void setLiked(Set<User> liked) {
        this.liked = liked;
    }
}
