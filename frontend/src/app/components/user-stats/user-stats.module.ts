import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {UserStatsComponent} from "./user-stats.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule
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
