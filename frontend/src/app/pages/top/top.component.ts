import {Component, OnInit} from "@angular/core";
import {styles} from "./top.component.styles";
import {AuthService} from "../../core/services/auth.service";

@Component({
    selector: 'mpt-top',
    templateUrl: './top.component.html',
})
export class TopComponent implements OnInit {

    styles: any = styles;
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
