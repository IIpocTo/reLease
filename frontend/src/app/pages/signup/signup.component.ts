import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {FormGroup, FormControl, Validators} from "@angular/forms";
import {UserService} from "../../core/services/user.service";
import {ToastService} from "../../core/toast/toast.service";
import {EMAIL_PATTERN, Validators as AppValidators} from "../../core/forms/index";
import values from "lodash/values";

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
                private toastService: ToastService) {
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

    private initForm() {
        this.login = new FormControl('', Validators.compose([
            Validators.required,
            Validators.minLength(4),
        ]));
        this.email = new FormControl('', Validators.compose([
            Validators.required,
            Validators.pattern(EMAIL_PATTERN),
        ]));
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
                    this.toastService.error('This email or login is already taken.');
                }
                break;
            default:
                this.toastService.error('Something bad happened.');
        }
    }

}
