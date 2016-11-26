import {NgModule, ApplicationRef} from "@angular/core";
import {AppComponent} from "./app.component";
import {RouterModule, PreloadAllModules} from "@angular/router";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NoContentComponent} from "./pages/no-content/no-content.component";
import {TopModule} from "./pages/top/top.module";
import {HeaderModule} from "./components/header/header.module";
import {SharedModule} from "./shared/shared.module";
import {ROUTES} from "./app.routes";
import {CoreModule} from "./core/core.module";
import {createNewHosts, createInputTransfer, removeNgStyles} from "@angularclass/hmr";

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
        FormsModule,
        ReactiveFormsModule,
        CoreModule,
        TopModule,
        HeaderModule,
        SharedModule,
    ],
})
export class AppModule {

    // Hot Module Replacement feature https://github.com/AngularClass/angular2-hmr

    public appRef: ApplicationRef;

    constructor(appRef: ApplicationRef) {
        this.appRef = appRef;
    }

    hmrOnInit(store) {
        if (!store || !store.state) return;
        console.log('HMR store', store);
        console.log('store.state.data:', store.state.data);
        if ('restoreInputValues' in store) {
            store.restoreInputValues();
        }
        this.appRef.tick();
        delete store.state;
        delete store.restoreInputValues;
    }

    hmrOnDestroy(store) {
        const cmpLocation = this.appRef.components.map(cmp => cmp.location.nativeElement);
        store.disposeOldHosts = createNewHosts(cmpLocation);
        store.state = {data: 'yolo'};
        store.restoreInputValues = createInputTransfer();
        removeNgStyles();
    }

    hmrAfterDestroy(store) {
        store.disposeOldHosts();
        delete store.disposeOldHosts;
    }

}
