import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../../shared/shared.module";
import {ProductComponent} from "./product.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule,
    ],
    declarations: [
        ProductComponent,
    ],
    exports: [
        ProductComponent,
    ],
})
export class ProductModule {
}
