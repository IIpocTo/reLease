package re_lease.service_layer.review;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import re_lease.service_layer.enumerations.Mark;
import re_lease.service_layer.enumerations.ReviewStatus;

import javax.persistence.*;
import java.util.UUID;

import static re_lease.service_layer.enumerations.ReviewStatus.UNDER_CONSIDERATION;

@Entity
@Table(name = "review")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "REVIEW_ID", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "MARK", nullable = false)
    private Mark mark;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private ReviewStatus status;

    public Review(String title, String description, Mark mark) {
        this.description = description;
        this.mark = mark;
        this.title = title;
        this.status = UNDER_CONSIDERATION;
    }

    public Review(String title, Mark mark) {
        this.description = "";
        this.mark = mark;
        this.title = title;
        this.status = UNDER_CONSIDERATION;
    }
}
