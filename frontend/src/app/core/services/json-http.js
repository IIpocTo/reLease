"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var http_1 = require("@angular/http");
var core_1 = require("@angular/core");
var mergeAuthToken = function (options) {
    if (options === void 0) { options = {}; }
    var newOptions = new http_1.RequestOptions({}).merge(options);
    var newHeaders = new http_1.Headers(newOptions.headers);
    var jwt = localStorage.getItem('jwt');
    if (jwt) {
        newHeaders.set('authorization', "Bearer " + jwt);
    }
    newOptions.headers = newHeaders;
    return newOptions;
};
var JsonHttp = (function () {
    function JsonHttp(http) {
        this.http = http;
    }
    JsonHttp.prototype.get = function (url, options) {
        return this.http.get(url, mergeAuthToken(options));
    };
    JsonHttp.prototype.post = function (url, body, options) {
        return this.http.post(url, body, mergeAuthToken(options));
    };
    JsonHttp.prototype.put = function (url, body, options) {
        return this.http.put(url, body, mergeAuthToken(options));
    };
    JsonHttp.prototype.delete = function (url, options) {
        return this.http.delete(url, mergeAuthToken(options));
    };
    JsonHttp.prototype.patch = function (url, body, options) {
        return this.http.patch(url, body, mergeAuthToken(options));
    };
    JsonHttp.prototype.head = function (url, options) {
        return this.http.head(url, mergeAuthToken(options));
    };
    return JsonHttp;
}());
JsonHttp = __decorate([
    core_1.Injectable()
], JsonHttp);
exports.JsonHttp = JsonHttp;
