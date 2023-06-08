package al.brain.saleforce.restsaleforce.repositories;

import al.brain.saleforce.restsaleforce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByInternalNameContainingIgnoreCase(String keyword);

    List<Product> findByPriceBetween(float minPrice, float maxPrice);

    List<Product> findByStockBetween(int minStock, int maxStock);

}