package re_lease.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import re_lease.domain.UserStats;

@Value
@Builder
public class UserDTO {
    private final long id;
    private final String email;
    @NonNull private final String login;
    private final UserStats userStats;
}
