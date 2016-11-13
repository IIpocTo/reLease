package re_lease.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import re_lease.domain.User;

import java.util.Optional;

@Component
public interface TokenHandler {
    Optional<UserDetails> parseUserFromToken(String token);
    String createTokenForUser(User user);
}
