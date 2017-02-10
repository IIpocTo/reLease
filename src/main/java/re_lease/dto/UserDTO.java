package re_lease.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import re_lease.domain.UserStats;

@Value
@Builder
public class UserDTO {
    @NonNull private final Long id;
    @NonNull private final String email;
    @NonNull private final String login;
    private final String avatarHash;
    private final UserStats userStats;
}
