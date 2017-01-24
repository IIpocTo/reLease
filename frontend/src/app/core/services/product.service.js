"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var helpers_1 = require("./helpers");
var url = '/api/products';
var url_list = '/api/users';
var defaultPageRequest = { page: 1, size: 5 };
var ProductService = (function () {
    function ProductService(http) {
        this.http = http;
    }
    ProductService.prototype.list = function (id, pageRequest) {
        if (pageRequest === void 0) { pageRequest = defaultPageRequest; }
        return this.http
            .get(url_list + "/" + id + "/products", { search: helpers_1.objToSearchParams(pageRequest) })
            .map(function (res) { return res.json(); });
    };
    ProductService.prototype.delete = function (id) {
        return this.http
            .delete(url + "/" + id);
    };
    ProductService.prototype.create = function (params) {
        return this.http
            .post(url, params);
    };
    return ProductService;
}());
ProductService = __decorate([
    core_1.Injectable()
], ProductService);
exports.ProductService = ProductService;
