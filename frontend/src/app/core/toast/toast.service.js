"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var rxjs_1 = require("rxjs");
var ToastService = (function () {
    function ToastService() {
        this.toastEvents = new rxjs_1.Subject();
    }
    Object.defineProperty(ToastService.prototype, "events", {
        get: function () {
            return this.toastEvents;
        },
        enumerable: true,
        configurable: true
    });
    ToastService.prototype.success = function (message) {
        this.publish({ message: message, level: 'success' });
    };
    ToastService.prototype.warning = function (message) {
        this.publish({ message: message, level: 'warning' });
    };
    ToastService.prototype.error = function (message) {
        this.publish({ message: message, level: 'error' });
    };
    ToastService.prototype.publish = function (toast) {
        this.toastEvents.next(toast);
    };
    ToastService = __decorate([
        core_1.Injectable()
    ], ToastService);
    return ToastService;
}());
exports.ToastService = ToastService;
