package re_lease.service_layer.product_type;

import lombok.*;
import re_lease.service_layer.product.Product;
import re_lease.service_layer.product_category.ProductCategory;
import re_lease.service_layer.product_photo.ProductPhoto;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "type")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductType {

    @Id
    @GeneratedValue
    @Column(name = "TYPE_ID", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private ProductCategory category;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productType",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public ProductType(String title, ProductCategory category) {
        this.title = title;
        this.category = category;
    }
}
