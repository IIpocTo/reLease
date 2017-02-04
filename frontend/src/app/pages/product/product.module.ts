import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {ProductComponent} from "./product.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule
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
