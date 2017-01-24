import {Component, OnInit, Input} from "@angular/core";
import {Product} from "../../core/domains"
import {styles} from "./goods.component.styles";
import {ProductService} from "../../core/services/product.service";
import {HttpErrorHandler} from "../../core/services/http-error-handler";

@Component({
    selector: 'mpt-goods',
    templateUrl: 'goods.component.html',
})
export class GoodsComponent implements OnInit {

    styles: any = styles;
    @Input()
    userId: string;
    products: Product[];

    constructor(private productService: ProductService, private errorHandler: HttpErrorHandler) {
        this.products = [];
    }

    ngOnInit(): any {
        this.productService
            .list(this.userId)
            .subscribe(
                products => {
                    this.products = products;
                },
                e => this.errorHandler.handle(e)
            );
    }

}
