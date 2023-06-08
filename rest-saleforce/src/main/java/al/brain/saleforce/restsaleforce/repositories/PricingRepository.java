package al.brain.saleforce.restsaleforce.repositories;

import al.brain.saleforce.restsaleforce.models.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricingRepository extends JpaRepository<Pricing, Integer> {

    List<Pricing> findByEnabled(int enabled);

}