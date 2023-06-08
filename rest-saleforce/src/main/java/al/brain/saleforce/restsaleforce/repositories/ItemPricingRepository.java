package al.brain.saleforce.restsaleforce.repositories;

import al.brain.saleforce.restsaleforce.models.ItemPricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPricingRepository extends JpaRepository<ItemPricing, Integer> {
    List<ItemPricing> findByEnabled(int enabled);
}
