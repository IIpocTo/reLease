import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {StylesDirective} from "./directives/styles.directive";

@NgModule({
    imports: [
        CommonModule,
    ],
    declarations: [
        StylesDirective,
    ],
    exports: [
        StylesDirective,
    ],
})
export class SharedModule {
}
