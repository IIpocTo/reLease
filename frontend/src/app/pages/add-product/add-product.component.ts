import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {ProductService} from "../../core/services/product.service";
import {FormControl, FormGroup} from "@angular/forms";
import * as toastr from "toastr";

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
        this.productForm = new FormGroup({
            title: new FormControl(),
            description: new FormControl(),
            price: new FormControl()
        });
    }

    public addProduct(params) {
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

}
