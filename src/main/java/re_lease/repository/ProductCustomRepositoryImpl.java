package re_lease.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import re_lease.domain.*;
import re_lease.dto.PageParams;
import re_lease.repository.helper.UserStatsQueryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final QProduct qProduct = QProduct.product;

    @Autowired
    public ProductCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Row> findByUser(User user, PageParams pageParams) {
        final QProduct qProduct = QProduct.product;
        Long page;
        Long size;
        if (pageParams.getPage().isPresent()) {
            page = pageParams.getPage().get();
        } else {
            return null;
        }
        if (pageParams.getSize().isPresent()) {
            size = pageParams.getSize().get();
        } else {
            return null;
        }
        List<Product> products = queryFactory.selectFrom(qProduct)
                .where(qProduct.productLeaser.eq(user))
                .orderBy(qProduct.id.asc()).fetch();
        List<Product> result = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if (i + 1 > (page - 1) * size && i + 1 <= page * size) {
                result.add(products.get(i));
            }
        }

        return result
                .stream()
                .map(product -> {
                            final QUser qUser = QUser.user;
                            Long productAmount = queryFactory.select(qProduct.id.count())
                                    .from(qProduct, qUser)
                                    .where(qUser.id.eq(product.getProductLeaser().getId()).and(qProduct.productLeaser.id.eq(qUser.id)))
                                    .groupBy(qUser)
                                    .fetchOne();

                            UserStats userStats = new UserStats(productAmount);
                            return Row.builder()
                                    .product(product)
                                    .userStats(userStats)
                                    .build();
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Row> findAll(PageParams pageParams) {
        final QProduct qProduct = QProduct.product;
        Long page;
        Long size;
        if (pageParams.getPage().isPresent()) {
            page = pageParams.getPage().get();
        } else {
            return null;
        }
        if (pageParams.getSize().isPresent()) {
            size = pageParams.getSize().get();
        } else {
            return null;
        }
        return queryFactory.selectFrom(qProduct)
                .where(qProduct.id.gt((page - 1) * size))
                .orderBy(qProduct.id.asc())
                .limit(size)
                .fetch()
                .stream()
                .map(product -> {
                            final QUser qUser = QUser.user;
                            Long productAmount = queryFactory.select(qProduct.id.count())
                                    .from(qProduct, qUser)
                                    .where(qUser.id.eq(product.getProductLeaser().getId()).and(qProduct.productLeaser.id.eq(qUser.id)))
                                    .groupBy(qUser)
                                    .fetchOne();

                            UserStats userStats = new UserStats(productAmount);

                            return Row.builder()
                                    .product(product)
                                    .userStats(userStats)
                                    .build();
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Row> findOne(Long id) {
        final ConstructorExpression<UserStats> userStatsExpression =
                UserStatsQueryHelper.userStatsExpression(qProduct.productLeaser);

        final Tuple row = queryFactory.select(qProduct, userStatsExpression)
                .from(qProduct)
                .where(qProduct.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(row).map(tuple -> Row.builder()
                .product(tuple.get(qProduct))
                .userStats(tuple.get(userStatsExpression))
                .build());
    }

}
