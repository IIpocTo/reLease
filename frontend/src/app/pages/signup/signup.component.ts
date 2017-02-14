import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {FormGroup, FormControl, Validators} from "@angular/forms";
import {UserService} from "../../core/services/user.service";
import {Validators as AppValidators, EMAIL_PATTERN} from "../../core/forms/index";
import values from "lodash/values";
import * as toastr from "toastr";
import {ValidationService} from "../../core/services/validation.service";
import {Observable} from "rxjs";

@Component({
    selector: 'mpt-signup',
    templateUrl: './signup.component.html'
})
export class SignupComponent implements OnInit {

    userForm: FormGroup;
    login: FormControl;
    email: FormControl;
    password: FormControl;
    passwordConfirmation: FormControl;

    constructor(private router: Router,
                private userService: UserService,
                private validationService: ValidationService) {
    }

    ngOnInit(): void {
        this.initForm();
    }

    onSubmit(params) {
        values(this.userForm.controls).forEach(c => c.markAsTouched());
        if (!this.userForm.valid) return;
        this.userService
            .create(params)
            .subscribe(() => {
                this.router.navigate(['/']);
            },
                e => this.handleError(e)
            );
    }

    validateEmail (c: FormControl):  Promise<any> | Observable<any> {
        const reg = new RegExp(EMAIL_PATTERN);
        if (reg.test(c.value)) {
            return new Promise<any>(
                (resolve, reject) => {
                            this.validationService.email(c.value)
                                .subscribe(
                                    data => {
                                        if (!data) {
                                            resolve({'validateEmail': true});
                                        } else {
                                            resolve(null);
                                        }
                                    });
                        });
            // return !this.emailResult ? {validateEmail: true} : {};
        } else {
            return new Promise((resolve) => { resolve({pattern: true}); });
        }
    }

    private initForm() {
        this.login = new FormControl('', Validators.compose([
            Validators.required,
            Validators.minLength(4),
        ]));
        this.email = new FormControl('', Validators.compose([
            Validators.required
        ]),  this.validateEmail);
        this.password = new FormControl('', Validators.compose([
            Validators.required,
            Validators.minLength(8),
        ]));
        this.passwordConfirmation = new FormControl('', Validators.compose([
            Validators.required,
        ]));
        this.userForm = new FormGroup({
                login: this.login,
                email: this.email,
                password: this.password,
                passwordConfirmation: this.passwordConfirmation,
            }, AppValidators.match(this.password, this.passwordConfirmation)
        );
    }

    private handleError(error) {
        switch (error.status) {
            case 400:
                if (error.json()['code'] === 'email_or_login_already_taken') {
                    toastr.error('Данный email или логин уже занят.');
                }
                break;
            default:
                toastr.error('Неизвестная ошибка.');
        }
    }

}
