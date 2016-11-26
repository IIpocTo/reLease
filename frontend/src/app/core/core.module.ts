import {XHRBackend, RequestOptions, Http, HttpModule} from "@angular/http";
import {JsonHttp} from "./services/json-http";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {UserService} from "./services/user.service";

export function createJsonHttp(xhrBackend: XHRBackend, requestOptions: RequestOptions) {
    const ngHttp = new Http(xhrBackend, requestOptions);
    return new JsonHttp(ngHttp);
}

@NgModule({
    imports: [
        CommonModule,
        HttpModule,
    ],
    providers: [
        {
            provide: JsonHttp,
            useFactory: createJsonHttp,
            deps: [XHRBackend, RequestOptions]
        },
        UserService,
    ]
})
export class CoreModule {}
