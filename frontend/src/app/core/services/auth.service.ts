import {Injectable} from "@angular/core";
import {Response} from "@angular/http";
import {Subject, Observable} from "rxjs";
import {JsonHttp} from "./json-http";
import {User} from "../domains";

const jwtDecode = require('jwt-decode');

@Injectable()
export class AuthService {

    private authEvents: Subject<AuthEvent>;

    constructor(private http: JsonHttp) {
        this.authEvents = new Subject<AuthEvent>();
    }

    login(login: string, password: string): Observable<Response> {
        const body = {
            login: login,
            password: password,
        };
        return this.http.post('/api/auth', body).do((resp: Response) => {
            localStorage.setItem('jwt', resp.json().token);
            this.authEvents.next(new DidLogin())
        });
    }

    logout(): void {
        localStorage.removeItem('jwt');
        this.authEvents.next(new DidLogout());
    }

    isSignedIn(): boolean {
        return localStorage.getItem('jwt') !== null;
    }

    isMyself(user: User): boolean | null {
        if (!this.isSignedIn()) return null;
        const decoded = jwtDecode(localStorage.getItem('jwt'));
        return user.id + '' === decoded.sub;
    }

    get events(): Observable<AuthEvent> {
        return this.authEvents;
    }

}

export class DidLogin {
}
export class DidLogout {
}
export type AuthEvent = DidLogin | DidLogout