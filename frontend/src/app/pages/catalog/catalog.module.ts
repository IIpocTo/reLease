import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {CatalogComponent} from "./catalog.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule
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
