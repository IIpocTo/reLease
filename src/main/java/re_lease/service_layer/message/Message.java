package re_lease.service_layer.message;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import re_lease.service_layer.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Message {

    @Id
    @GeneratedValue
    @Column(name = "MESSAGE_ID", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "BODY", nullable = false)
    private String messageBody;

    @Column(name = "DISPATCH_TIME", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dispatchTime;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "SENDER_ID")
    private User sender;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "RECIPIENT_ID")
    private User recipient;

    public Message(String messageBody, LocalDateTime dispatchTime, User sender, User recipient) {
        this.dispatchTime = dispatchTime;
        this.messageBody = messageBody;
        this.recipient = recipient;
        this.sender = sender;
    }
}
