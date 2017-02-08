package re_lease.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import re_lease.domain.enumeration.Gender;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "PHONE"),
        @UniqueConstraint(columnNames = "EMAIL"),
        @UniqueConstraint(columnNames = "LOGIN")
})
@NoArgsConstructor
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    @Getter
    @Setter
    private Long id;

    @Column(name = "LOGIN", nullable = false)
    @Size(min = 4, max = 50)
    @Setter
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    @Size(min = 8, max = 100)
    private String password;

    @Column(name = "FIRST_NAME")
    @Getter
    @Setter
    private String firstName;

    @Column(name = "LAST_NAME")
    @Getter
    @Setter
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    @Getter
    @Setter
    private Gender gender;

    @Column(name = "PHONE")
    @Getter
    @Setter
    private String phone;

    @Column(name = "ABOUT")
    @Getter
    @Setter
    private String about;

    @Column(name = "EMAIL", nullable = false)
    @Getter
    @Setter
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String email;

    @Column(name = "LAST_TIME_ACTIVE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Getter
    @Setter
    private LocalDateTime lastTimeActive;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "productLeaser",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Getter
    @Setter
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User(String login, String password, String firstName,
                String lastName, String phone, String email, Gender gender) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }

    @Override
    @JsonProperty("login")
    public String getUsername() {
        return login;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_USER");
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
