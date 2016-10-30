package re_lease.service_layer.product_category;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import re_lease.service_layer.product.Product;
import re_lease.service_layer.product_type.ProductType;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductCategory {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductType> types;

    public ProductCategory(String title) {
        this.title = title;
    }
}
