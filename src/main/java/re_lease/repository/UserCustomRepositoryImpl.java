package re_lease.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import re_lease.domain.QUser;
import re_lease.domain.User;
import re_lease.domain.UserStats;
import re_lease.repository.helper.UserStatsQueryHelper;

import java.util.Optional;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final QUser qUser = QUser.user;

    @Autowired
    public UserCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<Row> findOne(Long userId) {

        final ConstructorExpression<UserStats> userStatsExpression = UserStatsQueryHelper.userStatsExpression(qUser);

        final Tuple row = queryFactory.select(qUser, userStatsExpression)
                .from(qUser)
                .where(qUser.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(row).map(tuple -> Row.builder()
                .user(tuple.get(qUser))
                .userStats(tuple.get(userStatsExpression))
                .build());

    }

    @Override
    public Optional<String> checkUsernameExistence(String login) {
        final String returnedUsername = queryFactory.select(qUser.login)
                .from(qUser)
                .where(qUser.login.eq(login))
                .fetchOne();
        return Optional.ofNullable(returnedUsername);
    }

    @Override
    public Optional<String> checkEmailExistence(String email) {
        final String returnedEmail = queryFactory.select(qUser.email)
                .from(qUser)
                .where(qUser.email.eq(email))
                .fetchOne();
        return Optional.ofNullable(returnedEmail);
    }

}
