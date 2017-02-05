import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule} from "@angular/router";
import {UsageComponent} from "./usage.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
    ],
    declarations: [
        UsageComponent,
    ],
    exports: [
        UsageComponent,
    ],
})
export class UsageModule {
}
