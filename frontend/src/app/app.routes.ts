import {Routes} from "@angular/router";
import {TopComponent} from "./pages/top/top.component";
import {NoContentComponent} from "./pages/no-content/no-content.component";

export const ROUTES: Routes = [
    {path: '', component: TopComponent},
    {path: 'signup', loadChildren: './pages/signup/signup.module#SignupModule'},
    {path: '**', component: NoContentComponent},
];
