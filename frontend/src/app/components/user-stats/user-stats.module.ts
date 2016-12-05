import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../../shared/shared.module";
import {UserStatsComponent} from "./user-stats.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule,
    ],
    declarations: [
        UserStatsComponent,
    ],
    exports: [
        UserStatsComponent,
    ],
})
export class UserStatsModule {
}