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

    def "user can sign up"() {

        given:"user had not been registered yet"
        def login = "testLogin"
        def password = "somePassword"
        def email = "test@gmail.com"

        when:"user tries to sign up"
        def response = perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonOutput.toJson(email: email, password: password, login: login))
        )
        userService.create(new UserParams(email, password, login))

        then:"user successfully signs up"
        response.andExpect(MockMvcResultMatchers.status().isOk())

    }

    def "one can list users"() {

        given:"two users were registered"
        userService.findAll(_ as PageRequest) >> {
            List<UserDTO> content = (0..1).collect {
                User u = new User(id: it, login: "test${it}", password: "secretPass", email: "test${it}@test.com")
                UserDTO.builder()
                    .id(u.id)
                    .login(u.username)
                    .email(u.email)
                    .build()
            }
            return new PageImpl<>(content)
        }

        when:"user tries to get a user list"
        def response = perform(MockMvcRequestBuilders.get("/api/users"))

        then:"user successfully is able to list users"
        with(response) {
            andExpect(MockMvcResultMatchers.status().isOk())
            andExpect(MockMvcResultMatchers.jsonPath('$.content').exists())
            andExpect(MockMvcResultMatchers.jsonPath('$.content', hasSize(2)))
            andExpect(MockMvcResultMatchers.jsonPath('$.content[0].login', is("test0")))
            andExpect(MockMvcResultMatchers.jsonPath('$.content[0].email', is("test0@test.com")))
            andExpect(MockMvcResultMatchers.jsonPath('$.content[1].login', is("test1")))
        }

    }

}
