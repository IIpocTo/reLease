package re_lease.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import re_lease.domain.User;
import re_lease.dto.UserDTO;
import re_lease.dto.UserParams;
import re_lease.repository.UserCustomRepository;
import re_lease.repository.UserRepository;
import re_lease.utils.MD5Utils;

import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;
    private final SecurityContextService securityContextService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserCustomRepository userCustomRepository,
                           SecurityContextService securityContextService) {
        this.userRepository = userRepository;
        this.userCustomRepository = userCustomRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        return userCustomRepository.findOne(id).map(row -> {
            final Optional<User> currentUser = securityContextService.currentUser();
            final String email = currentUser.filter(user -> user.equals(row.getUser()))
                    .map(User::getEmail)
                    .orElse(null);
            return UserDTO.builder()
                    .id(row.getUser().getId())
                    .login(row.getUser().getUsername())
                    .email(email)
                    .avatarHash(MD5Utils.md5(row.getUser().getUsername()))
                    .userStats(row.getUserStats())
                    .build();
        });
    }

    @Override
    public Optional<UserDTO> findMe() {
        return securityContextService.currentUser().flatMap(user -> findOne(user.getId()));
    }

    @Override
    public Page<UserDTO> findAll(PageRequest pageable) {
        return userRepository.findAll(pageable).map(user -> UserDTO.builder()
                .id(user.getId())
                .login(user.getUsername())
                .email(user.getEmail())
                .avatarHash(MD5Utils.md5(user.getUsername()))
                .build()
        );
    }

    @Override
    public User create(UserParams params) {
        return userRepository.save(params.toUser());
    }

    @Override
    public User update(User user, UserParams params) {
        params.getEmail().ifPresent(user::setEmail);
        params.getEncodedPassword().ifPresent(user::setPassword);
        params.getLogin().ifPresent(user::setLogin);
        return userRepository.save(user);
    }

    @Override
    public User updateMe(UserParams params) {
        return securityContextService.currentUser()
                .map(user -> update(user, params))
                .orElseThrow(() -> new AccessDeniedException(""));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findOneByLogin(username);
        final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        user.ifPresent(detailsChecker::check);
        return user.orElseThrow(() -> new UsernameNotFoundException("user not found."));
    }
}
