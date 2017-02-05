import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule} from "@angular/router";
import {GoodsComponent} from "./goods.component";
import {ConfirmationModule} from "../confirmation/confirmation.module";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        ConfirmationModule,
    ],
    declarations: [
        GoodsComponent,
    ],
    exports: [
        GoodsComponent,
    ],
})
export class GoodsModule {
}
