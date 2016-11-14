package re_lease.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.core.Authentication
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.ResultActions
import re_lease.auth.TokenAuthenticationService
import re_lease.auth.UserAuthentication
import re_lease.domain.User
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import javax.servlet.http.HttpServletRequest

@ActiveProfiles("test")
@ComponentScan(basePackages = ["re_lease.auth"])
class BaseControllerTest extends Specification {

    @TestConfiguration
    static class Config {

        @Bean
        DetachedMockFactory detachedMockFactory() {
            return new DetachedMockFactory()
        }

    }

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService

    ResultActions perform(RequestBuilder requestBuilder) {
        return mockMvc.perform(requestBuilder)
    }

    User signIn(User user) {
        Authentication auth = new UserAuthentication(user)
        tokenAuthenticationService.getAuthentication(_ as HttpServletRequest) >> auth
        return user
    }

    User signIn() {
        User user = new User(id: 1, login: "testLogin", password: "goodPassword", email: "test@gmail.com")
        Authentication auth = new UserAuthentication(user)
        tokenAuthenticationService.getAuthentication(_ as HttpServletRequest) >> auth
        return user
    }

}
