import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {AuthService} from "../../core/services/auth.service";
import {ToastService} from "../../core/toast/toast.service";
import {styles} from "./auth.component.styles";

@Component({
    selector: 'mpt-auth',
    templateUrl: './auth.component.html',
})
export class AuthComponent {

    styles: any = styles;

    constructor(private router: Router,
                private authService: AuthService,
                private toastService: ToastService) {
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
                this.toastService.error('Login or password is wrong.');
                break;
            default:
                this.toastService.error('Something bad happened.');
        }
    }

}
