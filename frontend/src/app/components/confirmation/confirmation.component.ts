import {Component, Input, EventEmitter, Output} from "@angular/core";
import {Product} from "../../core/domains";

@Component({
    selector: 'mpt-confirmation',
    templateUrl: 'confirmation.component.html',
})
export class ConfirmationComponent {

    @Input()
    product: Product;
    @Input()
    title: string;
    @Input()
    description: string;

    @Output()
    onProductDelete = new EventEmitter<string>();


    deleteProduct(id) {
        this.onProductDelete.emit(id);
    }
}
