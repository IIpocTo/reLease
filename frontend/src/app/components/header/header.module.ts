import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule} from "@angular/router";
import {HeaderComponent} from "./header.component";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule,
    ],
    declarations: [
        HeaderComponent,
    ],
    exports: [
        HeaderComponent,
    ],
})
export class HeaderModule {
}
