package re_lease.service

import re_lease.domain.User
import re_lease.repository.BaseRepositoryTest

abstract class BaseServiceTest extends BaseRepositoryTest {

    SecurityContextService securityContextService = Mock(SecurityContextService)

    Optional<User> currentUser

    def setup() {
        currentUser = Optional.empty()
        securityContextService.currentUser() >> {currentUser}
    }

    def signIn(User user) {
        currentUser = Optional.of(user)
    }

    def cleanup() {
        currentUser = Optional.empty()
    }

}
