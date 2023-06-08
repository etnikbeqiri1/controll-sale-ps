package al.brain.saleforce.restsaleforce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemPricingNotFoundException extends RuntimeException {

    public ItemPricingNotFoundException(String message) {
        super(message);
    }
}
