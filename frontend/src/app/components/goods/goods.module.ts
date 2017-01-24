import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule} from "@angular/router";
import {GoodsComponent} from "./goods.component";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule,
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
