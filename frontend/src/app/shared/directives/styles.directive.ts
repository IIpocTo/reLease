import {Directive, ElementRef, Input, Renderer, OnChanges, SimpleChanges} from "@angular/core";
import {css} from "aphrodite";

@Directive({
    selector: '[mptStyles]',
})
export class StylesDirective implements OnChanges {

    @Input('mptStyles') mptStyles: any[];

    private el: ElementRef;
    private renderer: Renderer;

    constructor(el: ElementRef, renderer: Renderer) {
        this.el = el;
        this.renderer = renderer;
    }

    ngOnChanges(changes: SimpleChanges): void {
        let stylesVar = 'mptStyles';
        if (!changes[stylesVar].isFirstChange()) {
            const prevClassName = css(changes[stylesVar].previousValue);
            this.renderer.setElementClass(this.el.nativeElement, prevClassName, false);
        }
        const className = css(this.mptStyles);
        this.renderer.setElementClass(this.el.nativeElement, className, true);
    }

}
