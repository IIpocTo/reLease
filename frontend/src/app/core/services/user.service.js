"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var helpers_1 = require("./helpers");
var url = '/api/users';
var defaultPageRequest = { page: 1, size: 5 };
var UserService = (function () {
    function UserService(http) {
        this.http = http;
    }
    UserService.prototype.list = function (pageRequest) {
        if (pageRequest === void 0) { pageRequest = defaultPageRequest; }
        return this.http
            .get(url, { search: helpers_1.objToSearchParams(pageRequest) })
            .map(function (res) { return res.json(); });
    };
    UserService.prototype.get = function (id) {
        return this.http
            .get(url + "/" + id)
            .map(function (res) { return res.json(); });
    };
    UserService.prototype.create = function (params) {
        return this.http
            .post(url, params);
    };
    UserService = __decorate([
        core_1.Injectable()
    ], UserService);
    return UserService;
}());
exports.UserService = UserService;
