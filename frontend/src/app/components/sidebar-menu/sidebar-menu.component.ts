import {Component, Input, Output, EventEmitter} from "@angular/core";
import {styles} from "./sidebar-menu.component.styles";

@Component({
    selector: 'mpt-sidebar-menu',
    templateUrl: './sidebar-menu.component.html',
})
export class SidebarMenuComponent {

    @Input()
    pageType: string;
    @Output()
    onPageTypeChange = new EventEmitter<string>();

    styles: any = styles;

    showProducts() {
        this.pageType = "Goods";
        this.onPageTypeChange.emit(this.pageType);
    }

    showMessages() {
        this.pageType = "Messages";
        this.onPageTypeChange.emit(this.pageType);
    }

    showAccount() {
        this.pageType = "Account";
        this.onPageTypeChange.emit(this.pageType);
    }

    showReviews() {
        this.pageType = "Reviews";
        this.onPageTypeChange.emit(this.pageType);
    }
}
