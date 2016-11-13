package re_lease.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import re_lease.domain.User;
import re_lease.repository.UserRepository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Component
public class TokenHandlerImpl implements TokenHandler {

    private String secret;
    private UserRepository userRepository;

    @Autowired
    public TokenHandlerImpl(@Value("${app.jwt.secret}") String secret, UserRepository userRepository) {
        this.secret = secret;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDetails> parseUserFromToken(String token) {
        final String subject = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        final Long userId = Long.valueOf(subject);
        final User user = userRepository.findOne(userId);
        return Optional.ofNullable(user);
    }

    @Override
    public String createTokenForUser(User user) {
        final ZonedDateTime afterOneWeek = ZonedDateTime.now().plusWeeks(1);
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(Date.from(afterOneWeek.toInstant()))
                .compact();
    }
}
