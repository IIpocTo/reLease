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

    def "one can find paged user list"() {

        given:"two new users were saved to database"
        User user1 = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        User user2 = userRepository.save(
                new User(login: "test2Login", password: "testPassword", email: "sometest2@test.com")
        )

        when:"user requests the second page with only one user"
        PageRequest pageRequest = new PageRequest(1, 1)
        Page<UserDTO> page = userService.findAll(pageRequest)

        then:"user successfully gets requested page"
        page.content.first().id != user1.id
        page.content.first().id == user2.id
        page.totalElements == 2

    }

    def "one can find himself"() {

        given:"user successfully signed in"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        signIn(user)

        when:"user requests information about himself"
        UserDTO userDTO = userService.findMe().get()

        then:"user successfully receives information about himself"
        with(userDTO) {
            id == user.id
            email == user.email
            login == user.username
        }

    }

    def "system can create a user"() {

        given:"user successfully registered"
        UserParams params = new UserParams("somemail@gmail.com", "secretPassword", "testLogin")

        when:"system creates a new User instance"
        User user = userService.create(params)

        then:"user is successfully added to database"
        userRepository.count() == 1
        with(user) {
            id != null
            username == "testLogin"
            email == "somemail@gmail.com"
        }

    }

    def "one can update a user"() {

        given:"user successfully registered"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        UserParams params = new UserParams("somemail@gmail.com", "secretPass", "testLogin")

        when:"user tries to update his information"
        userService.update(user, params)

        then:"user successfully updates it"
        with(user) {
            username == "testLogin"
            email == "somemail@gmail.com"
        }

        when:"user tries to update only a part of his information"
        params = new UserParams("test2@ya.ru", null, null)
        userService.update(user, params)

        then:"user successfully updates just that part"
        with(user) {
            username == "testLogin"
            email == "test2@ya.ru"
        }

    }

    def "one can update himself"() {

        given:"user successfully registered and updated information about himself"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )
        UserParams params = new UserParams("somemail@gmail.com", "secretPass", "testLogin")
        signIn(user)

        when:"user updates his info"
        userService.updateMe(params)

        then:"user successfully updates his information"
        with(user) {
            username == "testLogin"
            email == "somemail@gmail.com"
        }

    }

    def "system can load user by his username"() {

        given:"system saved a new User to database"
        User user = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )

        when:"system tries to get the User by an existent login"
        UserDetails userDetails = userService.loadUserByUsername("testLogin")

        then:"system returns a matching User"
        user.username == userDetails.username

        when:"system tries to get User by nonexistent login"
        userService.loadUserByUsername("anotherTestLogin")

        then:"system throws an exception"
        thrown(UsernameNotFoundException)

    }

}
