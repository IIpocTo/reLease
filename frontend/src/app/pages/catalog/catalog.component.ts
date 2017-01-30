import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {styles} from "./catalog.component.styles";
import {HttpErrorHandler} from "../../core/services/http-error-handler";
import {ProductService} from "../../core/services/product.service";
import {Product} from "../../core/domains";
import {PageRequest} from "../../core/dto";

@Component({
    selector: 'mpt-catalog',
    templateUrl: './catalog.component.html',
})
export class CatalogComponent implements OnInit {

    styles: any = styles;

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
