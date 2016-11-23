import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {AppModule} from "./app";
import {bootloader} from "@angularclass/hmr";

export function main(): Promise<any> {
    return platformBrowserDynamic()
        .bootstrapModule(AppModule)
        .catch(err => console.error(err));
}

bootloader(main);
