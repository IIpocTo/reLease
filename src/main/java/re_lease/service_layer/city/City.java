package re_lease.service_layer.city;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import re_lease.service_layer.metro_station.Metro;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "city", uniqueConstraints = {
        @UniqueConstraint(columnNames = "TITLE")
})
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class City {

    @Id
    @GeneratedValue
    @Column(name = "CITY_ID", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Metro> metroStations;

    public City(String title) {
        this.title = title;
    }
}
