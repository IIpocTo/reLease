package re_lease.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import re_lease.repository.UserCustomRepository;

@Service("validationService")
public class ValidationServiceImpl implements ValidationService {

    private final UserCustomRepository userCustomRepository;

    @Autowired
    public ValidationServiceImpl(UserCustomRepository userCustomRepository) {
        this.userCustomRepository = userCustomRepository;
    }

    @Override
    public boolean validateUsername(String username) {
        return !this.userCustomRepository.checkUsernameExistence(username).isPresent();
    }

    @Override
    public boolean validateEmail(String email) {
        return !this.userCustomRepository.checkEmailExistence(email).isPresent();
    }

}
