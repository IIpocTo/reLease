import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {FormGroup, FormControl, Validators} from "@angular/forms";
import {UserService} from "../../core/services/user.service";
import {ValidationService} from "../../core/services/validation.service";
import {EMAIL_PATTERN, Validators as AppValidators} from "../../core/forms/index";
import values from "lodash/values";
import * as toastr from "toastr";

const minLoginLength: number = 4;
const minPasswordLength: number = 8;

@Component({
    selector: 'mpt-signup',
    templateUrl: './signup.component.html',
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
            .subscribe(
                () => this.router.navigate(['/']),
                e => this.handleError(e)
            );
    }

    private validateUsername(control: FormControl): Promise<any> {
        if (control.value.length >= minLoginLength) {
            return new Promise<any>(resolve => {
                this.validationService.checkUsername(control.value)
                    .subscribe(data => {
                            if (!data) {
                                resolve({'validateUsername': true});
                            } else {
                                resolve(null);
                            }
                        },
                        e => this.handleError(e)
                    );
            });
        } else {
            return new Promise(resolve => resolve({minlength: true}));
        }
    }

    private validateEmail(control: FormControl): Promise<any> {
        const reg = new RegExp(EMAIL_PATTERN);
        if (reg.test(control.value)) {
            return new Promise<any>(resolve => {
                this.validationService.checkEmail(control.value)
                    .subscribe(data => {
                            if (!data) {
                                resolve({'validateEmail': true});
                            } else {
                                resolve(null);
                            }
                        },
                        e => this.handleError(e)
                    );
            });
        } else {
            return new Promise(resolve => resolve({pattern: true}));
        }
    }

    private initForm() {
        this.login = new FormControl('', Validators.required, this.validateUsername.bind(this));
        this.email = new FormControl('', Validators.required, this.validateEmail.bind(this));
        this.password = new FormControl('', Validators.compose([
            Validators.required,
            Validators.minLength(minPasswordLength),
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
        toastr.error('Критическая ошибка - ' + error);
    }

}
