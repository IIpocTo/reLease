import {Component, OnInit, Input} from "@angular/core";
import {Product} from "../../core/domains";
import {styles} from "./goods.component.styles";
import {ProductService} from "../../core/services/product.service";
import {HttpErrorHandler} from "../../core/services/http-error-handler";
import {Router} from "@angular/router";
import {PageRequest} from "../../core/dto";

@Component({
    selector: 'mpt-goods',
    templateUrl: 'goods.component.html',
})
export class GoodsComponent implements OnInit {

    styles: any = styles;
    @Input() userId: string;
    products: Product[];
    currentPage: number;
    totalPages: number;

    constructor(private productService: ProductService,
                private errorHandler: HttpErrorHandler,
                private router: Router) {
        this.products = [];
        this.currentPage = 1;
    }

    ngOnInit(): any {
        this.productService
            .list(this.userId, new PageRequest(this.currentPage, 5))
            .subscribe(
                page => {
                    this.products = page.content;
                    this.currentPage = page.currentPage;
                    this.totalPages = page.totalPages;
                },
                e => this.errorHandler.handle(e)
            );
    }

    addProductNavigate() {
        this.router.navigate(['/add_product']);
    }

    deleteProduct(id) {
        this.productService
            .delete(id)
            .subscribe(
                () => {
                    this.productService
                        .list(this.userId, new PageRequest(this.currentPage, 5))
                        .subscribe(
                            page => {
                                this.products = page.content;
                                this.currentPage = page.currentPage;
                                this.totalPages = page.totalPages;
                            },
                            e => this.errorHandler.handle(e)
                        );
                }
            );
    }

    lookAtProduct(product) {
        this.router.navigate(['/product/' + product]);
    }

    nextPage(): any {
        this.currentPage++;
        this.productService
            .list("all", new PageRequest(this.currentPage, 5))
            .subscribe(
                page => {
                    this.products = page.content;
                    this.currentPage = page.currentPage;
                    this.totalPages = page.totalPages;
                },
                e => this.errorHandler.handle(e)
            );
    }

    prevPage(): any {
        this.currentPage--;
        this.productService
            .list("all", new PageRequest(this.currentPage, 5))
            .subscribe(
                page => {
                    this.products = page.content;
                    this.currentPage = page.currentPage;
                    this.totalPages = page.totalPages;
                },
                e => this.errorHandler.handle(e)
            );
    }

}
