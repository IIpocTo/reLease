package re_lease.service_layer.deal;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import re_lease.service_layer.enumerations.DealStatus;
import re_lease.service_layer.product.Product;
import re_lease.service_layer.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deal")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Deal {

    @Id
    @GeneratedValue
    @Column(name = "DEAL_ID", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "CONFIRMATION_DATE", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime confirmationDate;

    @Column(name = "EXPIRATION_DATE", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private DealStatus status;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product dealObject;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "LESSOR_ID")
    private User lessor;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "LESSEE_ID")
    private User lessee;

    public Deal(LocalDateTime confirmationDate, LocalDateTime expirationDate, User lessor, User lessee, Product dealObject) {
        this.confirmationDate = confirmationDate;
        this.expirationDate = expirationDate;
        this.lessor = lessor;
        this.lessee = lessee;
        this.dealObject = dealObject;
    }
}
