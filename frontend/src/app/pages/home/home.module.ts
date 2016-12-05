import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";
import {HomeComponent} from "./home.component";
import {UserStatsModule} from "../../components/user-stats/user-stats.module";

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        UserStatsModule,
    ],
    declarations: [
        HomeComponent,
    ],
    exports: [
        HomeComponent,
    ],
})
export class HomeModule {
}