package al.brain.saleforce.restsaleforce.services;
import al.brain.saleforce.restsaleforce.dtos.ItemPricingDTO;
import al.brain.saleforce.restsaleforce.dtos.ItemPricingUpdateDTO;
import al.brain.saleforce.restsaleforce.exceptions.ItemPricingNotFoundException;
import al.brain.saleforce.restsaleforce.models.ItemPricing;
import al.brain.saleforce.restsaleforce.repositories.ItemPricingRepository;
import al.brain.saleforce.restsaleforce.repositories.PricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemPricingService {

    private final ItemPricingRepository itemPricingRepository;
    private final PricingRepository pricingRepository;

    @Autowired
    public ItemPricingService(ItemPricingRepository itemPricingRepository, PricingRepository pricingRepository) {
        this.itemPricingRepository = itemPricingRepository;
        this.pricingRepository = pricingRepository;
    }

    public List<ItemPricing> getAllItemPricing() {
        return itemPricingRepository.findAll();
    }

    public List<ItemPricing> getEnabledItemPricing() {
        return itemPricingRepository.findByEnabled(1);
    }

    public List<ItemPricing> getDisabledItemPricing() {
        return itemPricingRepository.findByEnabled(1);
    }

    public Optional<ItemPricing> getItemPricingById(Integer id) {
        return itemPricingRepository.findById(id);
    }

    public void updateItemPricing(Integer id, ItemPricingUpdateDTO updatedItemPricingDTO) {
        Optional<ItemPricing> optionalItemPricing = itemPricingRepository.findById(id);
        if (optionalItemPricing.isPresent()) {
            ItemPricing existingItemPricing = optionalItemPricing.get();
            existingItemPricing.setName(updatedItemPricingDTO.getName());
            existingItemPricing.setEnabled(updatedItemPricingDTO.getEnabled());
            // Update any other fields as needed
            itemPricingRepository.save(existingItemPricing);
        } else {
            throw new ItemPricingNotFoundException("Item Pricing not found with id: " + id);
        }
    }



    public void deleteItemPricing(Integer id) {
        Optional<ItemPricing> optionalItemPricing = itemPricingRepository.findById(id);
        if (optionalItemPricing.isPresent()) {
            itemPricingRepository.delete(optionalItemPricing.get());
        } else {
            throw new ItemPricingNotFoundException("Item Pricing not found with id: " + id);
        }
    }

    public void enableItemPricing(Integer id) {
        Optional<ItemPricing> optionalItemPricing = itemPricingRepository.findById(id);
        if (optionalItemPricing.isPresent()) {
            ItemPricing itemPricing = optionalItemPricing.get();
            itemPricing.setEnabled(1);
            itemPricingRepository.save(itemPricing);
        } else {
            throw new ItemPricingNotFoundException("Item Pricing not found with id: " + id);
        }
    }

    public void disableItemPricing(Integer id) {
        Optional<ItemPricing> optionalItemPricing = itemPricingRepository.findById(id);
        if (optionalItemPricing.isPresent()) {
            ItemPricing itemPricing = optionalItemPricing.get();
            itemPricing.setEnabled(0);
            itemPricingRepository.save(itemPricing);
        } else {
            throw new ItemPricingNotFoundException("Item Pricing not found with id: " + id);
        }
    }

    public ItemPricingDTO createItemPricing(ItemPricingDTO itemPricingDTO) {
        ItemPricing itemPricing = new ItemPricing();
        itemPricing.setName(itemPricingDTO.getName());

        ItemPricing createdItemPricing = itemPricingRepository.save(itemPricing);

        ItemPricingDTO createdItemPricingDTO = new ItemPricingDTO();
        createdItemPricingDTO.setName(createdItemPricing.getName());
        return createdItemPricingDTO;
    }


}

