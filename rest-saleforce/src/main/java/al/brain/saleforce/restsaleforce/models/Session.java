package al.brain.saleforce.restsaleforce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "paid", columnDefinition = "int default 0")
    private boolean paid;

    @Column(name = "startedAt", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "duration")
    private int duration;

    @Column(name = "endedAt")
    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pricing_id", nullable = false)
    @JsonIgnore()
    private Pricing pricing;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnore()
    private Item item;

    @PrePersist
    protected void onCreate() {
        startedAt = LocalDateTime.now();
    }

}