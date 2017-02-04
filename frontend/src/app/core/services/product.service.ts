import {Observable} from "rxjs/Observable";
import {Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {Product} from "../domains";
import {objToSearchParams} from "./helpers";
import {PageRequest, ProductParams, Page} from "../dto";
import {JsonHttp} from "./json-http";

const productUrl = '/api/products';
const userUrl = '/api/users';
const defaultPageRequest: PageRequest = {page: 1, size: 5};

@Injectable()
export class ProductService {

    constructor(private http: JsonHttp) {
    }

    list(id: string | number, pageRequest: PageRequest = defaultPageRequest): Observable<Page<Product>> {
        return this.http
            .get(
                `${userUrl}/${id}/products`,
                {
                    search: objToSearchParams(pageRequest)
                })
            .map(res => {
                return new Page<Product>(
                    res.json().content,
                    res.json().currentPage,
                    res.json().totalPages,
                    res.json().totalElements
                );
            });
    }

    delete(id: string | number): Observable<Response> {
        return this.http
            .delete(`${productUrl}/${id}`);
    }

    create(params: ProductParams): Observable<Response> {
        return this.http
            .post(productUrl, params);
    }

    get(id: number): Observable<Product> {
        return this.http
            .get(`${productUrl}/${id}`)
            .map(res => res.json());
    }

}
