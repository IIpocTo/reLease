import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {RouterModule, PreloadAllModules} from "@angular/router";
import {BrowserModule} from "@angular/platform-browser";
import {NoContentComponent} from "./pages/no-content/no-content.component";
import {TopModule} from "./pages/top/top.module";
import {HeaderModule} from "./components/header/header.module";
import {SharedModule} from "./shared/shared.module";
import {ROUTES} from "./app.routes";

@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AppComponent,
        NoContentComponent,
    ],
    imports: [
        BrowserModule,
        RouterModule.forRoot(ROUTES, {
            preloadingStrategy: PreloadAllModules,
        }),
        TopModule,
        HeaderModule,
        SharedModule,
    ],
})
export class AppModule {
}
