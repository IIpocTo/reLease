package re_lease.service_layer.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import re_lease.service_layer.deal.Deal;
import re_lease.service_layer.product_photo.ProductPhoto;
import re_lease.service_layer.product_type.ProductType;
import re_lease.service_layer.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "RATING", nullable = false, columnDefinition = "float default(0.0)")
    private Float rating = 0.0f;

    @LastModifiedDate
    @Column(name = "LAST_TIME_UPDATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastTimeUpdate;

    @Column(name = "VIEWS", nullable = false, columnDefinition = "integer default(0)")
    private Integer views = 0;

    @Column(name = "VIEWS_TODAY", nullable = false, columnDefinition = "integer default(0)")
    private Integer viewsToday = 0;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User productLeaser;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private ProductType productType;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dealObject",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deal> deals;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPhoto> photos;

    public Product(Integer price, String title, User productLeaser, ProductType type) {
        this.price = price;
        this.title = title;
        this.productLeaser = productLeaser;
        this.productType = type;
    }

}
