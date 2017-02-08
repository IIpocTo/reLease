package re_lease.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User productLeaser;

    public Product(Integer price, String title, String description) {
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public Product(Integer price, String title, String description, User productLeaser) {
        this.price = price;
        this.title = title;
        this.description = description;
        this.productLeaser = productLeaser;
    }

}
