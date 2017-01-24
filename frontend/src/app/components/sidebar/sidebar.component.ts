import {Component, OnInit, Input, Output, EventEmitter} from "@angular/core";
import {User} from "../../core/domains";
import {HttpErrorHandler} from "../../core/services/http-error-handler";
import {UserService} from "../../core/services/user.service";
import {styles} from "./sidebar.component.styles";

@Component({
    selector: 'mpt-sidebar',
    templateUrl: './sidebar.component.html',
})
export class SidebarComponent implements OnInit{

    @Input()
    userId: string;

    @Input()
    pageType: string;
    @Output()
    onPageTypeChange = new EventEmitter<string>();

    styles: any = styles;
    user: User;

    constructor(private userService: UserService, private errorHandler: HttpErrorHandler) {
    }

    ngOnInit(): any {
        this.userService
            .get(this.userId)
            .subscribe(user => this.user = user, e => this.errorHandler.handle(e));
    }

    pageTypeChanged(event: string) {
        this.pageType = event;
        this.onPageTypeChange.emit(this.pageType);
    }



}
