"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var common_1 = require("@angular/common");
var ng_module_1 = require("@angular/core/src/metadata/ng_module");
var styles_directive_1 = require("./directives/styles.directive");
var SharedModule = (function () {
    function SharedModule() {
    }
    SharedModule = __decorate([
        ng_module_1.NgModule({
            imports: [
                common_1.CommonModule,
            ],
            declarations: [
                styles_directive_1.StylesDirective
            ],
            exports: [
                styles_directive_1.StylesDirective
            ],
        })
    ], SharedModule);
    return SharedModule;
}());
exports.SharedModule = SharedModule;
