import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {SidebarComponent} from "./sidebar.component";
import {SidebarMenuModule} from "../sidebar-menu/sidebar-menu.module";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SidebarMenuModule,
    ],
    declarations: [
        SidebarComponent,
    ],
    exports: [
        SidebarComponent,
    ],
})
export class SidebarModule {
}
