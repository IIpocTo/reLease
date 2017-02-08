import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {HomeComponent} from "./home.component";
import {SidebarModule} from "../../components/sidebar/sidebar.module";
import {GoodsModule} from "../../components/goods/goods.module";
import {AccountModule} from "../../components/account/account.module";
import {HomeResolver} from "./home.resolver";
import {AddProductModule} from "../add-product/add-product.module";

@NgModule({
    imports: [
        CommonModule,
        SidebarModule,
        GoodsModule,
        AddProductModule,
        AccountModule
    ],
    declarations: [
        HomeComponent,
    ],
    providers: [
        HomeResolver
    ],
    exports: [
        HomeComponent,
    ],
})
export class HomeModule {
}
