import {Component, Input, Output, EventEmitter} from "@angular/core";

@Component({
    selector: 'mpt-menu-element',
    templateUrl: './menu-element.component.html',
})
export class MenuElementComponent {

    @Input()
    description: string;
    @Input()
    name: string;
    @Output()
    nameChange = new EventEmitter<string>();

    public showMe() {
        this.nameChange.emit(this.name);
    }

}
