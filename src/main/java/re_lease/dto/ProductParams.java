package re_lease.dto;

import lombok.Value;
import re_lease.domain.Product;

@Value
public class ProductParams {

    private Integer price;
    private String title;
    private String description;

    public Product toProduct() {
        return new Product(price, title, description);
    }

}
