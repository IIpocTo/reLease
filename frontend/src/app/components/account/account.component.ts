import {Component, Input} from "@angular/core";
import {User} from "../../core/domains";

@Component({
    selector: 'mpt-account',
    styleUrls: ['account.component.scss'],
    templateUrl: 'account.component.html',
})
export class AccountComponent {

    @Input() user: User;

}
