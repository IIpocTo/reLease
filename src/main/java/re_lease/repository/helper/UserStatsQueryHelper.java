package re_lease.repository.helper;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import re_lease.domain.QProduct;
import re_lease.domain.QUser;
import re_lease.domain.UserStats;

public class UserStatsQueryHelper {

    public static ConstructorExpression<UserStats> userStatsExpression(QUser qUser) {
        return Projections.constructor(
                UserStats.class,
                countProductsQuery(qUser)
        );
    }

    private static JPQLQuery<Long> countProductsQuery(QUser qUser) {
        final QProduct qProduct = new QProduct("product_count");
        return JPAExpressions.select(qProduct.count())
                .from(qProduct)
                .where(qProduct.productLeaser.eq(qUser));
    }

}
