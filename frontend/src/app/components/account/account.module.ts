import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule} from "@angular/router";
import {AccountComponent} from "./account.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule
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
