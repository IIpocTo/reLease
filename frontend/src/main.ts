import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {bootloader} from "@angularclass/hmr";
import {AppModule} from "./app";
import "bootstrap/dist/js/bootstrap";
import {decorateModuleRef} from "./app/environment";
import * as toastr from "toastr";

export function main(): Promise<any> {
    toastr.options.preventDuplicates = true;
    return platformBrowserDynamic()
        .bootstrapModule(AppModule)
        .then(decorateModuleRef)
        .catch(err => console.error(err));
}

bootloader(main);
