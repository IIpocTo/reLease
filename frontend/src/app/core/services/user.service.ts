import {Observable} from "rxjs/Observable";
import {Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {User} from "../domains";
import {objToSearchParams} from "./helpers";
import {PageRequest, Page, UserParams} from "../dto";
import {JsonHttp} from "./json-http";

const url = '/api/users';
const defaultPageRequest: PageRequest = {page: 1, size: 5};

@Injectable()
export class UserService {

    constructor(private http: JsonHttp) {
    }

    list(pageRequest: PageRequest = defaultPageRequest): Observable<Page<User>> {
        return this.http
            .get(url, {search: objToSearchParams(pageRequest)})
            .map(res => res.json());
    }

    get(id: string | number): Observable<User> {
        return this.http
            .get(`${url}/${id}`)
            .map(res => res.json());
    }

    create(params: UserParams): Observable<Response> {
        return this.http
            .post(url, params);
    }

}
