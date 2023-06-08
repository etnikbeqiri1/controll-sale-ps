package al.brain.saleforce.restsaleforce.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PricingUpdateDTO {
    private String name;
    private float price;
    private float startPrice;
    private int enabled;
}
