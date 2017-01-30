import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {SharedModule} from "../../shared/shared.module";
import {CatalogComponent} from "./catalog.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule,
    ],
    declarations: [
        CatalogComponent,
    ],
    exports: [
        CatalogComponent,
    ],
})
export class CatalogModule {
}
