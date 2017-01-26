import {Observable} from "rxjs/Observable";
import {Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {Product} from "../domains";
import {objToSearchParams} from "./helpers";
import {PageRequest, ProductParams} from "../dto";
import {JsonHttp} from "./json-http";

const url = '/api/products';
const url_list = '/api/users';
const defaultPageRequest: PageRequest = {page: 1, size: 5};

@Injectable()
export class ProductService {

    constructor(private http: JsonHttp) {
    }

    list(id: string | number, pageRequest: PageRequest = defaultPageRequest): Observable<Product[]> {
        return this.http
            .get(
                `${url_list}/${id}/products`,
                {
                    search: objToSearchParams(pageRequest)
                })
            .map(
                res => res.json()
            );
    }

    delete(id: string | number): Observable<Response> {
        return this.http
            .delete(`${url}/${id}`);
    }

    create(params: ProductParams): Observable<Response> {
        return this.http
            .post(url, params);
    }

    get(id: number): Observable<Product> {
        return this.http
            .get(`${url}/${id}`)
            .map(res => res.json());
    }

}


