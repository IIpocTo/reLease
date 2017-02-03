import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../core/services/auth.service";
import {styles} from "./top.component.styles";

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
