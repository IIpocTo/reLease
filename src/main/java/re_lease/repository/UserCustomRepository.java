package re_lease.repository;

import org.springframework.data.repository.Repository;
import re_lease.domain.User;

import java.util.Optional;

public interface UserCustomRepository extends Repository<User, Long> {
    Optional<User> findOne(Long userId);
}
