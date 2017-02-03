import {Component, Input, Output, EventEmitter} from "@angular/core";
import {styles} from "./menu-element.component.styles";

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
    styles: any = styles;

    public showMe() {
        this.nameChange.emit(this.name);
        this.applyStyle();
    }

    private applyStyle() {

    }

}
