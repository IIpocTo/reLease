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
        switch (error.status) {
            case 401:
                toastr.error('Login or password is wrong.');
                break;
            default:
                toastr.error('Something bad happened.');
        }
    }

}
