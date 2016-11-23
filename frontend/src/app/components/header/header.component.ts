import {Component} from "@angular/core";
import {styles} from "./header.component.styles";

@Component({
    selector: 'mpt-header',
    templateUrl: 'header.component.html',
})
export class HeaderComponent {
    styles: any = styles;
}
