import {XHRBackend, RequestOptions, Http, HttpModule} from "@angular/http";
import {JsonHttp} from "./services/json-http";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {UserService} from "./services/user.service";
import {ToastModule} from "./toast/toast.module";
import {AuthService} from "./services/auth.service";
import {ToastComponent} from "./toast/toast.component";
import {HttpErrorHandler} from "./services/http-error-handler";
import {PublicPageGuard} from "./services/public-page.guard";
import {PrivatePageGuard} from "./services/private-page.guard";

export function createJsonHttp(xhrBackend: XHRBackend, requestOptions: RequestOptions) {
    const ngHttp = new Http(xhrBackend, requestOptions);
    return new JsonHttp(ngHttp);
}

@NgModule({
    imports: [
        CommonModule,
        HttpModule,
        ToastModule,
    ],
    exports: [
      ToastComponent,
    ],
    providers: [
        {
            provide: JsonHttp,
            useFactory: createJsonHttp,
            deps: [XHRBackend, RequestOptions],
        },
        UserService,
        AuthService,
        HttpErrorHandler,
        PublicPageGuard,
        PrivatePageGuard,
    ],
})
export class CoreModule {

}
