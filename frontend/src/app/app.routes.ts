import {Routes} from "@angular/router";
import {TopComponent} from "./pages/top/top.component";
import {NoContentComponent} from "./pages/no-content/no-content.component";
import {AuthComponent} from "./pages/auth/auth.component";
import {HomeComponent} from "./pages/home/home.component";
import {PublicPageGuard} from "./core/services/public-page.guard";
import {PrivatePageGuard} from "./core/services/private-page.guard";

export const ROUTES: Routes = [
    {path: '', component: TopComponent},
    {path: 'login', component: AuthComponent, canActivate: [PublicPageGuard]},
    {path: 'signup', loadChildren: './pages/signup/signup.module#SignupModule', canActivate: [PublicPageGuard]},
    {path: 'home', component: HomeComponent, canActivate: [PrivatePageGuard]},
    {path: '**', component: NoContentComponent},
];
