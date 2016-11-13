package re_lease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re_lease.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLogin(String login);
}
