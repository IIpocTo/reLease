import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {SignupComponent} from "./signup.component";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ValidationService} from "../../core/services/validation.service";

const routes: Routes = [
    {path: '', component: SignupComponent},
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        SignupComponent
    ],
    providers: [
        ValidationService
    ],
    exports: [
        SignupComponent,
    ]
})
export class SignupModule {
}
