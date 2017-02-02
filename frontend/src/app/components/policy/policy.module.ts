import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../../shared/shared.module";
import {PolicyComponent} from "./policy.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule
    ],
    declarations: [
        PolicyComponent,
    ],
    exports: [
        PolicyComponent,
    ],
})
export class PolicyModule {
}
