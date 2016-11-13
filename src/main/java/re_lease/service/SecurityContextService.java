package re_lease.service;

import re_lease.domain.User;

import java.util.Optional;

public interface SecurityContextService {
    Optional<User> currentUser();
}
