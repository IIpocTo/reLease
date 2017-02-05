import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {MenuElementComponent} from "./menu-element.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule
    ],
    declarations: [
        MenuElementComponent,
    ],
    exports: [
        MenuElementComponent,
    ],
})
export class MenuElementModule {
}
