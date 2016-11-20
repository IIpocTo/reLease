package re_lease.repository;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.repository.Repository;
import re_lease.domain.Product;
import re_lease.domain.User;
import re_lease.domain.UserStats;
import re_lease.dto.PageParams;

import java.util.List;

public interface ProductCustomRepository extends Repository<Product, Long> {

    List<Row> findByUser(User user, PageParams pageParams);

    @Value
    @Builder
    class Row {
        private final Product product;
        private final UserStats userStats;
    }

}
