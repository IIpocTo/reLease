import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule} from "@angular/router";
import {PolicyComponent} from "./policy.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
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
