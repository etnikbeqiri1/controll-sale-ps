package al.brain.saleforce.restsaleforce.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PricingDTO {
    private String name;
    private float price;
    private float startPrice;
    private int enabled;
    private int itemPricing;
}
