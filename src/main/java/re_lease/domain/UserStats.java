package re_lease.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserStats {

    private final Long productCount;

    @QueryProjection
    public UserStats(Long productCount) {
        this.productCount = productCount;
    }

}
