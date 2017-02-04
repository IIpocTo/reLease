import {Component, OnInit, Input} from "@angular/core";
import {User} from "../../core/domains";
import {UserService} from "../../core/services/user.service";
import {HttpErrorHandler} from "../../core/services/http-error-handler";

@Component({
    selector: 'mpt-user-stats',
    styleUrls: ['user-stats.component.scss'],
    templateUrl: 'user-stats.component.html',
})
export class UserStatsComponent implements OnInit {

    @Input()
    userId: string;

    user: User;

    constructor(private userService: UserService, private errorHandler: HttpErrorHandler) {
    }

    ngOnInit(): any {
        this.userService
            .get(this.userId)
            .subscribe(user => this.user = user, e => this.errorHandler.handle(e));
    }

}
