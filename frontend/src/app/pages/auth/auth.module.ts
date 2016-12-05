import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../../shared/shared.module";
import {AuthComponent} from "./auth.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule,
    ],
    declarations: [
        AuthComponent,
    ],
    exports: [
        AuthComponent,
    ],
})
export class AuthModule {
}