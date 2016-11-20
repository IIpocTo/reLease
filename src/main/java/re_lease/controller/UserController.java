package re_lease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import re_lease.domain.User;
import re_lease.dto.ErrorResponse;
import re_lease.dto.UserDTO;
import re_lease.dto.UserParams;
import re_lease.service.UserService;
import re_lease.service.exceptions.UserNotFoundException;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<UserDTO> list(@RequestParam(value = "page", required = false) @Nullable Integer page,
                              @RequestParam(value = "size", required = false) @Nullable Integer size) {
        final PageRequest pageable = new PageRequest(
                Optional.ofNullable(page).orElse(1) - 1,
                Optional.ofNullable(size).orElse(DEFAULT_PAGE_SIZE)
        );
        return userService.findAll(pageable);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User create(@Valid @RequestBody UserParams params) {
        return userService.create(params);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id:\\d+}")
    public UserDTO show(@PathVariable("id") Long id) throws UserNotFoundException {
        return userService.findOne(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/me")
    public UserDTO showMe() {
        return userService.findMe()
                .orElseThrow(() -> new AccessDeniedException(""));
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/me")
    public void updateMe(@Valid @RequestBody UserParams userParams) {
        userService.updateMe(userParams);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleValidationException(DataIntegrityViolationException e) {
        return new ErrorResponse("email_or_login_already_taken", "This email or login is already taken.");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No user")
    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFound() {}

}
