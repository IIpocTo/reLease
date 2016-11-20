package re_lease.repository

import org.springframework.beans.factory.annotation.Autowired
import re_lease.domain.User

class UserCustomRepositoryTest extends BaseRepositoryTest {

    @Autowired
    UserRepository userRepository

    @Autowired
    UserCustomRepository userCustomRepository

    def "findOne"() {

        given:"creating and saving new User to database"
        User savedUser = userRepository.save(
                new User(login: "testLogin", password: "testPassword", email: "sometest@test.com")
        )

        when:"trying to find savedUser by id"
        UserCustomRepository.Row result = userCustomRepository.findOne(savedUser.id).get()

        then:"they must be the same"
        with(result) {
            user == savedUser
            userStats.productCount == 0l
        }

    }

}
