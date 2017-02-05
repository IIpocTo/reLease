import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule} from "@angular/router";
import {ConfirmationComponent} from "./confirmation.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule
    ],
    declarations: [
        ConfirmationComponent,
    ],
    exports: [
        ConfirmationComponent,
    ],
})
export class ConfirmationModule {
}
