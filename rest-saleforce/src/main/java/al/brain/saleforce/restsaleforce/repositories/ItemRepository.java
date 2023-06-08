package al.brain.saleforce.restsaleforce.repositories;

import al.brain.saleforce.restsaleforce.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>, JpaSpecificationExecutor<Item> {
    List<Item> findByState(int i);
}