package re_lease.controller

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import re_lease.domain.User
import re_lease.dto.UserDTO
import re_lease.dto.UserParams
import re_lease.service.UserService
import spock.mock.DetachedMockFactory

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.nullValue

@WebMvcTest(UserController)
class UserControllerTest extends BaseControllerTest {

    @TestConfiguration
    static class Config {

        @Bean
        UserService userService(DetachedMockFactory factory) {
            return factory.Mock(UserService)
        }

    }

    @Autowired
    UserService userService

    def "can signup"() {

        given:
        def login = "testLogin"
        def password = "somePassword"
        def email = "test@gmail.com"

        when:
        def response = perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonOutput.toJson(email: email, password: password, login: login))
        )
        userService.create(new UserParams(email, password, login))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())

    }

    def "can not signup if login or email is duplicated"() {

        given:
        def login = "testLogin"
        def password = "somePassword"
        def email = "test@gmail.com"
        userService.create(_ as UserParams) >> {
            throw new DataIntegrityViolationException("")
        }

        when:
        def response = perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonOutput.toJson(email: email, password: password, login: login))
        )

        then:
        with(response) {
            andExpect(MockMvcResultMatchers.status().isBadRequest())
            andExpect(MockMvcResultMatchers.jsonPath('$.code', is("email_or_login_already_taken")))
        }

    }

    def "can list users"() {

        given:
        userService.findAll(_ as PageRequest) >> {
            List<UserDTO> content = (0..1).collect {
                User u = new User(id: it, login: "test${it}", password: "secretPass", email: "test${it}@test.com")
                UserDTO.builder()
                    .id(u.id)
                    .login(u.username)
                    .build()
            }
            return new PageImpl<>(content)
        }

        when:
        def response = perform(MockMvcRequestBuilders.get("/api/users"))

        then:
        with(response) {
            andExpect(MockMvcResultMatchers.status().isOk())
            andExpect(MockMvcResultMatchers.jsonPath('$.content').exists())
            andExpect(MockMvcResultMatchers.jsonPath('$.content', hasSize(2)))
            andExpect(MockMvcResultMatchers.jsonPath('$.content[0].login', is("test0")))
            andExpect(MockMvcResultMatchers.jsonPath('$.content[0].email', nullValue()))
            andExpect(MockMvcResultMatchers.jsonPath('$.content[1].login', is("test1")))
        }

    }

}
