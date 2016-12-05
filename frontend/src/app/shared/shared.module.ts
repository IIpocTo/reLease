import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {StylesDirective} from "./directives/styles.directive";
import {PluralizePipe} from "./pipes/pluralize.pipe";

@NgModule({
    imports: [
        CommonModule,
    ],
    declarations: [
        StylesDirective,
        PluralizePipe,
    ],
    exports: [
        StylesDirective,
        PluralizePipe,
    ],
})
export class SharedModule {
}
