package re_lease.service_layer.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import re_lease.service_layer.enumerations.Gender;
import re_lease.service_layer.product.Product;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "PHONE"),
        @UniqueConstraint(columnNames = "EMAIL")
})
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "LOGIN", nullable = false)
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "AVATAR")
    private byte[] avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", nullable = false)
    private Gender gender;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "ABOUT")
    private String about;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "MARK")
    private Float mark;

    @Column(name = "LAST_TIME_ACTIVE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastTimeActive;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productLeaser",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

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

}
