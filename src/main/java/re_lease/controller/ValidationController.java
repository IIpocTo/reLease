package re_lease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import re_lease.service.ValidationService;

@RestController
@RequestMapping("api/validate")
public class ValidationController {

    private final ValidationService validationService;

    @Autowired
    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/email")
    public boolean validate(@RequestHeader String email) {
        return this.validationService.validateEmail(email);
    }
}
