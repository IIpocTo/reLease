import {FormControl, ValidatorFn} from "@angular/forms";
import {ValidationService} from "../services/validation.service";

export class Validators {

    public emailResult: boolean;

    static match(c1: FormControl, c2: FormControl): ValidatorFn {
        return (): {[key: string]: any} => {
            if (c1.value !== c2.value) return {unmatched: true};
            return {};
        };
    }

    checkEmail(email: string, validationService: ValidationService): void {
        validationService.email(email)
            .subscribe(
                res => {
                    this.emailResult = res;
                }
            );
    }
}
