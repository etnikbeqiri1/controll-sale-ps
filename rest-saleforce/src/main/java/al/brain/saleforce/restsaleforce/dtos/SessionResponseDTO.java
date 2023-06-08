package al.brain.saleforce.restsaleforce.dtos;

import al.brain.saleforce.restsaleforce.models.Item;
import al.brain.saleforce.restsaleforce.models.Pricing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SessionResponseDTO {
    private UUID id;
    private int timeActive;
    private Pricing price;
    private int itemId;
    private String itemName;
    private int itemState;
    private boolean isPaid;
    private double finalPrice;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
