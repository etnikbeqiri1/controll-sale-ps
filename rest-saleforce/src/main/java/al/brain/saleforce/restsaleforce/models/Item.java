package al.brain.saleforce.restsaleforce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "item")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "state", columnDefinition = "int default 0")
    private int state;

    @Column(name = "enabled", nullable = false, columnDefinition = "int default 1")
    private int enabled = 1;

    @ManyToOne()
    @JoinColumn(name = "item_pricing_id", nullable = false)
    private ItemPricing itemPricing;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}