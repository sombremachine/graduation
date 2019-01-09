package com.topjava.graduation.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users1", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractBaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;


    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    // https://stackoverflow.com/a/12505165/548473
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade=CascadeType.ALL)//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Filter(name="min_id", condition=":minid <= id")
    @Where(clause = "datediff('ms', date_time, now()) < 10000")
    private List<Vote> votes;

    public User() {
    }

    public User(Integer id, String name, @Email @NotBlank @Size(max = 100) String email, @NotBlank @Size(min = 5, max = 100) String password, Set<Role> roles, List<Vote> votes) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.votes = votes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    //    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public Date getRegistered() {
//        return registered;
//    }
//
//    public void setRegistered(Date registered) {
//        this.registered = registered;
//    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
