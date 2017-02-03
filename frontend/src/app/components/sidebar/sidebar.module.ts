import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../../shared/shared.module";
import {SidebarComponent} from "./sidebar.component";
import {SidebarMenuModule} from "../sidebar-menu/sidebar-menu.module";
import {UserStatsModule} from "../user-stats/user-stats.module";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule,
        SidebarMenuModule,
        UserStatsModule
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
