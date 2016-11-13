package re_lease.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import re_lease.domain.User;

import javax.validation.constraints.Size;
import java.util.Optional;

@ToString
@EqualsAndHashCode
public final class UserParams {

    private final String email;
    @Size(min = 8, max = 100)
    private final String password;
    private final String login;

    public UserParams(@JsonProperty("email") String email,
                      @JsonProperty("password") String password,
                      @JsonProperty("login") String login) {
        this.email = email;
        this.password = password;
        this.login = login;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getLogin() {
        return Optional.ofNullable(login);
    }

    public Optional<String> getEncodedPassword() {
        return Optional.ofNullable(password).map(pass -> new BCryptPasswordEncoder().encode(pass));
    }

    public User toUser() {
        return new User(login, new BCryptPasswordEncoder().encode(password), email);
    }

}
