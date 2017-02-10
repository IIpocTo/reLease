import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../core/domains";

@Component({
    selector: 'mpt-home',
    styleUrls: ['./home.component.scss'],
    templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

    user: User;
    pageType: string = "";

    constructor(private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.user = this.route.snapshot.data['user'];
    }

    pageTypeChanged(event: string) {
        this.pageType = event;
    }

}
