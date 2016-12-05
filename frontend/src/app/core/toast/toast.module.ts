import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";
import {ToastService} from "./toast.service";
import {ToastComponent} from "./toast.component";

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
    ],
    providers: [
        ToastService,
    ],
    declarations: [
        ToastComponent,
    ],
    exports: [
        ToastComponent,
    ],
})
export class ToastModule {
}
