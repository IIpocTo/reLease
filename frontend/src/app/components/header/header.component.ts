import {Component, OnInit, HostListener} from "@angular/core";
import {styles} from "./header.component.styles";
import {Router} from "@angular/router";
import {AuthService} from "../../core/services/auth.service";

@Component({
    selector: 'mpt-header',
    templateUrl: 'header.component.html',
})
export class HeaderComponent implements OnInit {

    styles: any = styles;
    isSignedIn: boolean;

    constructor(private router: Router, private authService: AuthService) {
    }

    ngOnInit(): void {
        this.isSignedIn = this.authService.isSignedIn();
        this.authService.events.subscribe(() => {
            this.isSignedIn = this.authService.isSignedIn();
        });
    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

}
