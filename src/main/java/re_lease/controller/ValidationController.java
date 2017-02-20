package re_lease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import re_lease.service.ValidationService;

@RestController
@RequestMapping("api/validate")
public class ValidationController {

    private final ValidationService validationService;

    @Autowired
    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/username")
    public boolean validateUsername(@RequestHeader String username) {
        return validationService.validateUsername(username);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/email")
    public boolean validateEmail(@RequestHeader String email) {
        return validationService.validateEmail(email);
    }

}
