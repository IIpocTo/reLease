import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../../shared/shared.module";
import {SidebarMenuComponent} from "./sidebar-menu.component";
import {MenuElementModule} from "../menu-element/menu-element.module";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule,
        MenuElementModule
    ],
    declarations: [
        SidebarMenuComponent,
    ],
    exports: [
        SidebarMenuComponent,
    ],
})
export class SidebarMenuModule {
}
