package re_lease.service_layer.metro_station;

import lombok.*;
import re_lease.service_layer.city.City;
import re_lease.service_layer.product.Product;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "metro")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Metro {

    @Id
    @GeneratedValue
    @Column(name = "STATION_ID", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    public Metro(String title, City city) {
        this.title = title;
        this.city = city;
    }
}
