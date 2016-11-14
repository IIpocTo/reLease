package re_lease.controller

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import re_lease.auth.TokenHandler
import re_lease.auth.UserAuthentication
import re_lease.domain.User
import re_lease.service.SecurityContextService
import spock.mock.DetachedMockFactory

import static org.hamcrest.Matchers.is

@WebMvcTest(AuthController)
class AuthControllerTest extends BaseControllerTest {

    @TestConfiguration
    static class Config {

        @Bean
        AuthenticationManager authenticationManager(DetachedMockFactory factory) {
            return factory.Mock(AuthenticationManager)
        }

        @Bean
        TokenHandler tokenHandler(DetachedMockFactory factory) {
            return factory.Mock(TokenHandler)
        }

        @Bean
        SecurityContextService securityContextService(DetachedMockFactory factory) {
            return factory.Mock(SecurityContextService)
        }

    }

    @Autowired
    AuthenticationManager authenticationManager

    @Autowired
    TokenHandler tokenHandler

    @Autowired
    SecurityContextService securityContextService

    def setup() {

        User user = new User(id: 1, login: "testLogin", password: "goodPassword", email: "test@gmail.com")
        UsernamePasswordAuthenticationToken loginToken =
                new UsernamePasswordAuthenticationToken("testLogin", "goodPassword")
        authenticationManager.authenticate(loginToken) >> new UserAuthentication(user)
        authenticationManager.authenticate(_ as Authentication) >> {
            throw new BadCredentialsException("")
        }
        securityContextService.currentUser() >> Optional.of(user)
        tokenHandler.createTokenForUser(user) >> "created jwt"

    }

    def "can auth with correct login and password"() {

        when:
        def response = perform(MockMvcRequestBuilders.post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonOutput.toJson(login: "testLogin", password: "goodPassword"))
        )

        then:
        with(response) {
            andExpect(MockMvcResultMatchers.status().isOk())
            andExpect(MockMvcResultMatchers.jsonPath('$.token', is("created jwt")))
        }

    }

    def "can not auth when login or password is not valid"() {

        when:
        def response = perform(MockMvcRequestBuilders.post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonOutput.toJson(login: "invalidLogin", password: "goodPassword"))
        )

        then:
        with(response) {
            andExpect(MockMvcResultMatchers.status().isUnauthorized())
        }

    }

}
