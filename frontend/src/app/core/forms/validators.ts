import {FormControl, ValidatorFn} from "@angular/forms";

export class Validators {

    static match(c1: FormControl, c2: FormControl): ValidatorFn {
        return (): {[key: string]: any} => {
            if (c1.value !== c2.value) return {unmatched: true};
            return {};
        };
    }

    static positive(c: FormControl) : ValidatorFn {
        return () : {[key: string]: any} => {
            if (isNaN(parseInt(c.value)) || parseInt(c.value) >= 0) {
                return {};
            } else {
                return {negative: true};
            }
        };
    }

}
