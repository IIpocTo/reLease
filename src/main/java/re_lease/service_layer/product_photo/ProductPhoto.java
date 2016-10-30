package re_lease.service_layer.product_photo;

import lombok.*;
import re_lease.service_layer.product.Product;
import re_lease.service_layer.user.User;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "photo")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductPhoto {

    @Id
    @GeneratedValue
    @Column(name = "PHOTO_ID", columnDefinition = "uuid")
    private UUID id;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public ProductPhoto(byte[] photo, Product product) {
        this.photo = photo;
        this.product = product;
    }
}
