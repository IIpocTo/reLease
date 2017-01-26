import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ProductService} from "../../core/services/product.service";
import {styles} from "./product.component.styles";
import {Product} from "../../core/domains";
import {HttpErrorHandler} from "../../core/services/http-error-handler";

@Component({
    selector: 'mpt-product',
    templateUrl: './product.component.html',
})
export class ProductComponent implements OnInit {

    styles: any = styles;
    product: Product;

    constructor(private productService: ProductService,
                private route: ActivatedRoute,
                private errorHandler: HttpErrorHandler) {

    }

    ngOnInit(): any {
        let id = this.route.snapshot.params['productId'];
        this.productService
            .get(id)
            .subscribe(
                product => {
                    this.product = product;
                },
                e => this.errorHandler.handle(e)
            );
    }

}
