import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../core/services/auth.service";

@Component({
    selector: 'mpt-top',
    styleUrls: ['./top.component.scss'],
    templateUrl: './top.component.html',
})
export class TopComponent implements OnInit {

    isSignedIn: boolean;

    constructor(private authService: AuthService) {
    }

    ngOnInit(): void {
        this.isSignedIn = this.authService.isSignedIn();
        this.authService.events.subscribe(() => {
            this.isSignedIn = this.authService.isSignedIn();
        });
    }

}
