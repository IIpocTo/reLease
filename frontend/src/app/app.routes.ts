import {Routes} from "@angular/router";
import {NoContentComponent} from "./pages/no-content/no-content.component";
import {AuthComponent} from "./pages/auth/auth.component";
import {HomeComponent} from "./pages/home/home.component";
import {PublicPageGuard} from "./core/services/public-page.guard";
import {PrivatePageGuard} from "./core/services/private-page.guard";
import {AddProductComponent} from "./pages/add-product/add-product.component";
import {ProductComponent} from "./pages/product/product.component";
import {TopComponent} from "./pages/top/top.component";
import {CatalogComponent} from "./pages/catalog/catalog.component";
import {HomeResolver} from "./pages/home/home.resolver";

export const ROUTES: Routes = [
    {path: '', component: TopComponent, canActivate: []},
    {path: 'login', component: AuthComponent, canActivate: [PublicPageGuard]},
    {path: 'signup', loadChildren: './pages/signup/signup.module#SignupModule', canActivate: [PublicPageGuard]},
    {path: 'home', component: HomeComponent, resolve: {user: HomeResolver}, canActivate: [PrivatePageGuard]},
    {path: 'catalog', component: CatalogComponent, canActivate: []},
    {path: 'add_product', component: AddProductComponent, canActivate: [PrivatePageGuard]},
    {path: 'product/:productId', component: ProductComponent, canActivate: []},
    {path: '**', component: NoContentComponent},
];
