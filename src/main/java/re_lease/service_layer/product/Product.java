package re_lease.service_layer.product;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import re_lease.service_layer.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product")
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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User productLeaser;

    public Product() {}

    public Product(Integer price, String title, User productLeaser) {
        this.price = price;
        this.title = title;
        this.productLeaser = productLeaser;
    }

    public UUID getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public LocalDateTime getLastTimeUpdate() {
        return lastTimeUpdate;
    }

    public void setLastTimeUpdate(LocalDateTime lastTimeUpdate) {
        this.lastTimeUpdate = lastTimeUpdate;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getViewsToday() {
        return viewsToday;
    }

    public void setViewsToday(Integer viewsToday) {
        this.viewsToday = viewsToday;
    }

    public User getProductLeaser() {
        return productLeaser;
    }

    public void setProductLeaser(User productLeaser) {
        this.productLeaser = productLeaser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
