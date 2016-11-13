package re_lease.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import re_lease.domain.User;
import re_lease.dto.UserDTO;
import re_lease.dto.UserParams;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<UserDTO> findOne(Long id);
    Optional<UserDTO> findMe();
    Page<UserDTO> findAll(PageRequest pageable);
    User create(UserParams params);
    User update(User user, UserParams params);
    User updateMe(UserParams params);
}
