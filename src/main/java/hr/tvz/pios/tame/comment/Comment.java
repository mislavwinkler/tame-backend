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
}
