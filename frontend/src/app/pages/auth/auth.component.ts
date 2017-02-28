import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {AuthService} from "../../core/services/auth.service";
import * as toastr from "toastr";

@Component({
    selector: 'mpt-auth',
    templateUrl: './auth.component.html',
})
export class AuthComponent {

    constructor(private router: Router,
                private authService: AuthService) {
    }

    logIn(login, password) {
        this.authService
            .login(login, password)
            .subscribe(() => {
                this.router.navigate(['/home']);
            },
                e => this.handleError(e)
            );
    }

    handleError(error) {
        const httpUnauthorizedCode = 401;
        switch (error.status) {
            case httpUnauthorizedCode:
                toastr.error('Неправильный логин или пароль.');
                break;
            default:
                toastr.error('Неизвестная ошибка.');
        }
    }

}
