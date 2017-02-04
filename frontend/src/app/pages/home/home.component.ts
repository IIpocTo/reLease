import {Component} from "@angular/core";

@Component({
    selector: 'mpt-home',
    styleUrls: ['./home.component.scss'],
    templateUrl: './home.component.html'
})
export class HomeComponent {

    pageType: string = "";

    pageTypeChanged(event: string) {
        this.pageType = event;
    }

}
