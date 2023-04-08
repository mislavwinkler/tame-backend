package hr.tvz.pios.tame.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.tvz.pios.tame.post.Post;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String firstname;

    private String lastname;

    @Column(name = "birth_date")
    private Date dateOfBirth;

    @Column(name = "registration_date")
    private Date dateOfRegistration;

    @Column(name = "profile_picture")
    private String profilePicture;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "USER_HAS_AUTHORITY",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")}
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "maker", fetch = FetchType.LAZY)
    private List<Post> postList;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "USER_IS_FOLLOWING",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "following_id", referencedColumnName = "id")}
    )
    @BatchSize(size = 20)
    private Set<User> following = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String email, String firstname, String lastname, Date dateOfBirth, String profilePicture) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.profilePicture = profilePicture;
    }

    public User(Long id, String username, String password, String email, String firstname, String lastname, Date dateOfBirth, Date dateOfRegistration, String profilePicture) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistration = dateOfRegistration;
        this.profilePicture = profilePicture;
    }

    public User(String username, String password, String email, String firstname, String lastname, Date dateOfBirth, Date dateOfRegistration, String profilePicture) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistration = dateOfRegistration;
        this.profilePicture = profilePicture;
    }

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getEmail() { return email; }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public Date getDateOfBirth() { return dateOfBirth; }

    public Date getDateOfRegistration() { return dateOfRegistration; }

    public String getProfilePicture() { return profilePicture; }

    public Set<Authority> getAuthorities() { return authorities; }

    public List<Post> getPostList() { return postList; }

    public Set<User> getFollowing() { return following; }
}


