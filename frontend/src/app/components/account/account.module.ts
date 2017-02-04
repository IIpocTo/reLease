import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule} from "@angular/router";
import {AccountComponent} from "./account.component";
import {UserStatsModule} from "../user-stats/user-stats.module";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        UserStatsModule
    ],
    declarations: [
        AccountComponent,
    ],
    exports: [
        AccountComponent,
    ],
})
export class AccountModule {
}
