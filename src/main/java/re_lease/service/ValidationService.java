package re_lease.service;

import org.springframework.stereotype.Service;
import re_lease.repository.UserCustomRepository;
import re_lease.repository.UserRepository;

@Service("validationService")
public class ValidationService {
    private final UserCustomRepository userCustomRepository;

    public ValidationService(UserCustomRepository userCustomRepository) {
        this.userCustomRepository = userCustomRepository;
    }

    public boolean validateEmail(String email) {
        return !this.userCustomRepository.findUserByEmail(email).isPresent();
    }
}
