package re_lease.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import re_lease.domain.QProduct;
import re_lease.domain.User;
import re_lease.dto.PageParams;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ProductCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Row> findByUser(User user, PageParams pageParams) {
        final QProduct qProduct = QProduct.product;
        return queryFactory.selectFrom(qProduct)
                .where(qProduct.productLeaser.eq(user)
                    .and(pageParams.getSinceId().map(qProduct.id::gt).orElse(null))
                    .and(pageParams.getMaxId().map(qProduct.id::lt).orElse(null))
                )
                .orderBy(qProduct.id.desc())
                .limit(pageParams.getCount())
                .fetch()
                .stream()
                .map(product -> Row.builder()
                        .product(product)
                        .build()
                )
                .collect(Collectors.toList());
    }

}
