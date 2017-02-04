import {Component, Input, Output, EventEmitter} from "@angular/core";
import {User} from "../../core/domains";

@Component({
    selector: 'mpt-sidebar',
    templateUrl: './sidebar.component.html',
})
export class SidebarComponent {

    @Input()
    user: User;

    @Input()
    pageType: string;

    @Output()
    onPageTypeChange = new EventEmitter<string>();

    pageTypeChanged(event: string) {
        this.pageType = event;
        this.onPageTypeChange.emit(this.pageType);
    }

}
