import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {ProductService} from "../../core/services/product.service";
import {ToastService} from "../../core/toast/toast.service";
import {styles} from "./add-product.component.styles";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
    selector: 'mpt-add-product',
    templateUrl: './add-product.component.html',
})
export class AddProductComponent implements OnInit {

    styles: any = styles;

    productForm: FormGroup;
    title: FormControl;
    description: FormControl;
    price: FormControl;

    constructor(private router: Router,
                private productService: ProductService,
                private toastService: ToastService) {
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
            .subscribe(value => {
                    console.log(value);
                    this.router.navigate(['/home']);
                },
                e => this.handleError(e)
            );
    }

    handleError(error) {
        switch (error.status) {
            case 401:
                this.toastService.error('Увы, какие-то данные были неправильными.');
                break;
            default:
                this.toastService.error('Что-то нехорошее случилось.');
        }
    }

}
