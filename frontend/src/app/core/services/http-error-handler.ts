import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {AuthService} from "./auth.service";
import * as toastr from "toastr";

@Injectable()
export class HttpErrorHandler {

    constructor(private router: Router, private authService: AuthService) {
    }

    handle(error: any) {
        const httpUnauthorizedCode: number = 401;
        if (error.status === httpUnauthorizedCode) {
            toastr.error('Please sign in');
            this.authService.logout();
            this.router.navigate(['login']);
        }
    }

}
