import {Injectable} from "@angular/core";
import {Resolve} from "@angular/router";
import {User} from "../../core/domains";
import {UserService} from "../../core/services/user.service";
import {Observable} from "rxjs";

@Injectable()
export class HomeResolver implements Resolve<User> {

    constructor(private userService: UserService) {
    }

    resolve(): Observable<User> {
        return this.userService.get('me');
    }

}
