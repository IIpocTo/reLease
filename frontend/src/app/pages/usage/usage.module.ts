import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {RouterModule, Routes} from "@angular/router";
import {UsageComponent} from "./usage.component";

const routes: Routes = [
    {path: '', component: UsageComponent},
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes)
    ],
    declarations: [
        UsageComponent
    ],
    exports: [
        UsageComponent
    ]
})
export class UsageModule {
}
