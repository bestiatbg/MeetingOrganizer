package com.meetingorganizer.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * User's table
 *
 * @author Aleksander
 */
@Entity
@Table(name = "USER")
public class User
        implements UserDetails {

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Column(name = "FIRSTNAME", nullable = false)
    @Getter
    @Setter
    private String firstName;

    @Column(name = "LASTNAME", nullable = false)
    @Getter
    @Setter
    private String lastName;

    @Column(name = "LOGIN", nullable = false, unique = true)
    @Getter
    @Setter
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    @Setter
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @Getter
    @Setter
    private String email;

    @Column(name = "PHONE")
    @Getter
    @Setter
    private String phone;

    @Column(name = "ENABLED")
    @Setter
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID")}
    )
    @Setter
    private Set<Authority> authorities;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
            , mappedBy = "users")
    @Getter
    @Setter
    private List<Meeting> meetings;

    public User() {
        enabled = true;
        authorities = new HashSet<>();
        meetings = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                '}';
    }
}
