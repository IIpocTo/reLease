"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var aphrodite_1 = require("aphrodite");
var StylesDirective = (function () {
    function StylesDirective(el, renderer) {
        this.el = el;
        this.renderer = renderer;
    }
    StylesDirective.prototype.ngOnChanges = function (changes) {
        var stylesVar = 'mptStyles';
        if (!changes[stylesVar].isFirstChange()) {
            var prevClassName = aphrodite_1.css(changes[stylesVar].previousValue);
            this.renderer.setElementClass(this.el.nativeElement, prevClassName, false);
        }
        var className = aphrodite_1.css(this.mptStyles);
        this.renderer.setElementClass(this.el.nativeElement, className, true);
    };
    __decorate([
        core_1.Input('mptStyles')
    ], StylesDirective.prototype, "mptStyles", void 0);
    StylesDirective = __decorate([
        core_1.Directive({
            selector: '[mptStyles]',
        })
    ], StylesDirective);
    return StylesDirective;
}());
exports.StylesDirective = StylesDirective;
