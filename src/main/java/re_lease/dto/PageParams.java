package re_lease.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class PageParams {

    private static final int DEFAULT_COUNT = 20;

    private Long page;
    private Long size;
    private int count = DEFAULT_COUNT;

    public Optional<Long> getPage() {
        return Optional.ofNullable(page);
    }

    public Optional<Long> getSize() {
        return Optional.ofNullable(size);
    }

}
