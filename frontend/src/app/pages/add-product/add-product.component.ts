import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {ProductService} from "../../core/services/product.service";
import {FormControl, FormGroup, Validators, ValidatorFn} from "@angular/forms";
import * as toastr from "toastr";
import values from "lodash/values";

@Component({
    selector: 'mpt-add-product',
    templateUrl: './add-product.component.html',
})
export class AddProductComponent implements OnInit {

    productForm: FormGroup;
    title: FormControl;
    description: FormControl;
    price: FormControl;

    constructor(private router: Router, private productService: ProductService) {
    }

    ngOnInit() {
       this.initForm();
    }

    onSubmit(params) {
        values(this.productForm.controls).forEach(c => c.markAsTouched());
        if (!this.productForm.valid) return;
        this.productService
            .create(params)
            .subscribe(() => {
                    this.router.navigate(['/home']);
                },
                e => this.handleError(e)
            );
    }

    handleError(error) {
        switch (error.status) {
            case 401:
                toastr.error('Увы, какие-то данные были неправильными.');
                break;
            default:
                toastr.error('Что-то нехорошее случилось.');
        }
    }

    private positive(c: FormControl) : ValidatorFn {
        return () : {[key: string]: any} => {
            if (isNaN(parseInt(c.value))) {
                return {};
            }
            if (parseInt(c.value) >= 0) {
                return {};
            } else {
                return {negative: true};
            }
        };
    }

    private initForm() {
        this.title = new FormControl('', Validators.required);
        this.description = new FormControl('', Validators.required);
        this.price = new FormControl('', Validators.compose([
            Validators.required,
           ]));
        this.productForm = new FormGroup({
            title: this.title,
            description: this.description,
            price: this.price
        },  this.positive(this.price));
    }

}
