package re_lease.repository

import org.springframework.beans.factory.annotation.Autowired
import re_lease.domain.User

class UserCustomRepositoryTest extends BaseRepositoryTest {

    @Autowired
    UserRepository userRepository

    @Autowired
    UserCustomRepository userCustomRepository

    def "system loads the same user it has saved before"() {

        given:"system had created and saved a new User to database"
        User savedUser = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )

        when:"system tries to find a saved User by id"
        UserCustomRepository.Row result = userCustomRepository.findOne(savedUser.id).get()

        then:"saved User and the loaded one must be equal"
        with(result) {
            user == savedUser
            userStats.productCount == 0l
        }

    }

}
