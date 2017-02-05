import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule} from "@angular/router";
import {GoodsComponent} from "./goods.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule
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
