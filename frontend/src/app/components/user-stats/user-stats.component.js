"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var user_stats_component_styles_1 = require("./user-stats.component.styles");
var UserStatsComponent = (function () {
    function UserStatsComponent(userService, errorHandler) {
        this.userService = userService;
        this.errorHandler = errorHandler;
        this.shownOnProfile = false;
        this.styles = user_stats_component_styles_1.styles;
    }
    UserStatsComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.userService
            .get(this.userId)
            .subscribe(function (user) { return _this.user = user; }, function (e) { return _this.errorHandler.handle(e); });
    };
    __decorate([
        core_1.Input()
    ], UserStatsComponent.prototype, "userId", void 0);
    __decorate([
        core_1.Input()
    ], UserStatsComponent.prototype, "shownOnProfile", void 0);
    UserStatsComponent = __decorate([
        core_1.Component({
            selector: 'mpt-user-stats',
            templateUrl: 'user-stats.component.html',
        })
    ], UserStatsComponent);
    return UserStatsComponent;
}());
exports.UserStatsComponent = UserStatsComponent;
