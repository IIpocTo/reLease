import {URLSearchParams} from "@angular/http";

export const objToSearchParams = (obj: any): URLSearchParams => {
    let params = new URLSearchParams();
    for (let i in obj) {
        if (obj.hasOwnProperty(i)) {
            if (obj[i]) {
                params.append(i, obj[i]);
            }
        }
    }
    return params;
};
