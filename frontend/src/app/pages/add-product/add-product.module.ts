import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {AddProductComponent} from "./add-product.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    declarations: [
        AddProductComponent,
    ],
    exports: [
        AddProductComponent,
    ],
})
export class AddProductModule {
}
