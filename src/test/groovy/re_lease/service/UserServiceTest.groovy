package re_lease.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import re_lease.domain.User
import re_lease.dto.UserDTO
import re_lease.dto.UserParams
import re_lease.repository.UserCustomRepository
import re_lease.repository.UserRepository

class UserServiceTest extends BaseServiceTest {

    @Autowired
    UserRepository userRepository

    @Autowired
    UserCustomRepository userCustomRepository

    UserService userService

    def setup() {
        userService = new UserServiceImpl(userRepository, userCustomRepository, securityContextService)
    }

    def "can find paged user list"() {

        given:"saved two new users to database"
        User user1 = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        User user2 = userRepository.save(
                new User(login: "test2Login", password: "testPassword", email: "sometest2@test.com")
        )

        when:"requesting second page with only one user"
        PageRequest pageRequest = new PageRequest(1, 1)
        Page<UserDTO> page = userService.findAll(pageRequest)

        then:"successfully get requested page"
        page.content.first().id != user1.id
        page.content.first().id == user2.id
        page.totalElements == 2

    }

    def "can find me"() {

        given:"user successfully sign in"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)

        when:"requesting info about himself"
        UserDTO userDTO = userService.findMe().get()

        then:"successfully return his info"
        with(userDTO) {
            id == user.id
            email == user.email
            login == user.username
        }

    }

    def "can create a user"() {

        given:"user successfully registered"
        UserParams params = new UserParams("somemail@gmail.com", "secretPassword", "testLogin")

        when:"creating new User instance"
        User user = userService.create(params)

        then:"user successfully added to database"
        userRepository.count() == 1
        with(user) {
            id != null
            username == "testLogin"
            email == "somemail@gmail.com"
        }

    }

    def "can update a user"() {

        given:"user successfully registered"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        UserParams params = new UserParams("somemail@gmail.com", "secretPass", "testLogin")

        when:"trying to update his info"
        userService.update(user, params)

        then:"successfully update it"
        with(user) {
            username == "testLogin"
            email == "somemail@gmail.com"
        }

        when:"trying to update only part of his info"
        params = new UserParams("test2@ya.ru", null, null)
        userService.update(user, params)

        then:"successfully update just that part"
        with(user) {
            username == "testLogin"
            email == "test2@ya.ru"
        }

    }

    def "can update me"() {

        given:"user successfully registered and updating info about himself"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        UserParams params = new UserParams("somemail@gmail.com", "secretPass", "testLogin")
        signIn(user)

        when:"updating his info"
        userService.updateMe(params)

        then:"successfully update it"
        with(user) {
            username == "testLogin"
            email == "somemail@gmail.com"
        }

    }

    def "loadUserByUsername"() {

        given:"saved new User to database"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )

        when:"trying to get User by existing login"
        UserDetails userDetails = userService.loadUserByUsername("testLogin")

        then:"returning matching User"
        user.username == userDetails.username

        when:"trying to get User by not existing login"
        userService.loadUserByUsername("anotherTestLogin")

        then:"throw exception"
        thrown(UsernameNotFoundException)

    }

}
