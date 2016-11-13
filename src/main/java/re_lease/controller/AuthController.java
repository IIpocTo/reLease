package re_lease.controller;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import re_lease.auth.TokenHandler;
import re_lease.service.SecurityContextService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenHandler tokenHandler;
    private final SecurityContextService securityContextService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          TokenHandler tokenHandler,
                          SecurityContextService securityContextService) {
        this.authenticationManager = authenticationManager;
        this.tokenHandler = tokenHandler;
        this.securityContextService = securityContextService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public AuthResponse auth(@RequestBody AuthParams params) {

        final UsernamePasswordAuthenticationToken loginToken = params.toAuthenticationToken();
        final Authentication authentication = authenticationManager.authenticate(loginToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return securityContextService.currentUser().map(u -> {
            final String token = tokenHandler.createTokenForUser(u);
            return new AuthResponse(token);
        }).orElseThrow(RuntimeException::new);

    }

    @Value
    private static final class AuthParams {

        private final String login;
        private final String password;

        UsernamePasswordAuthenticationToken toAuthenticationToken() {
            return new UsernamePasswordAuthenticationToken(login, password);
        }

    }

    @Value
    private static final class AuthResponse {
        private final String token;
    }

}
