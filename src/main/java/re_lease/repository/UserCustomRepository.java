package re_lease.repository;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.repository.Repository;
import re_lease.domain.User;
import re_lease.domain.UserStats;

import java.util.Optional;

public interface UserCustomRepository extends Repository<User, Long> {

    Optional<Row> findOne(Long userId);
    Optional<User> findUserByEmail(String email);

    @Value
    @Builder
    class Row {
        private final User user;
        private final UserStats userStats;
    }

}
