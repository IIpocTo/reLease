import {Injectable} from "@angular/core";
import {JsonHttp} from "./json-http";
import {Headers} from "@angular/http";
import {Observable} from "rxjs";

const url = '/api/validate';

@Injectable()
export class ValidationService {

    constructor(private http: JsonHttp) {
    }

    email(email: string): Observable<boolean> {
        let headers = new Headers();
        headers.append('email', email);
        return this.http
            .get(url + '/email', {headers: headers})
            .map(res => res.json());
    }

}
