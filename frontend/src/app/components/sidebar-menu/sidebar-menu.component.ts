import {Component, Input, Output, EventEmitter} from "@angular/core";

@Component({
    selector: 'mpt-sidebar-menu',
    templateUrl: './sidebar-menu.component.html',
})
export class SidebarMenuComponent {

    @Input()
    pageType: string;

    @Output()
    onPageTypeChange = new EventEmitter<string>();

    highlightedButtonIndex: number = 1;

    show(index: number) {
        switch (index) {
            case 1: {
                this.highlightedButtonIndex = 1;
                this.pageType = "Account";
                this.onPageTypeChange.emit(this.pageType);
                break;
            }
            case 2: {
                this.highlightedButtonIndex = 2;
                this.pageType = "Goods";
                this.onPageTypeChange.emit(this.pageType);
                break;
            }
            case 3: {
                this.highlightedButtonIndex = 3;
                this.pageType = "Reviews";
                this.onPageTypeChange.emit(this.pageType);
                break;
            }
            case 4: {
                this.highlightedButtonIndex = 4;
                this.pageType = "Messages";
                this.onPageTypeChange.emit(this.pageType);
                break;
            }
            default:
        }
    }

}
