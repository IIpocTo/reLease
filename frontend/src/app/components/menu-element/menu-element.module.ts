import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../../shared/shared.module";
import {MenuElementComponent} from "./menu-element.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule
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
