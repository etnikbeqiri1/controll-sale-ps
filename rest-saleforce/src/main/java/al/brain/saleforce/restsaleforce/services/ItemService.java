package al.brain.saleforce.restsaleforce.services;

import al.brain.saleforce.restsaleforce.dtos.ItemDTO;
import al.brain.saleforce.restsaleforce.exceptions.ItemNotFoundException;
import al.brain.saleforce.restsaleforce.models.Item;
import al.brain.saleforce.restsaleforce.models.ItemPricing;
import al.brain.saleforce.restsaleforce.models.Pricing;
import al.brain.saleforce.restsaleforce.repositories.ItemPricingRepository;
import al.brain.saleforce.restsaleforce.repositories.ItemRepository;
import al.brain.saleforce.restsaleforce.repositories.PricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemPricingRepository itemPricingRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, ItemPricingRepository itemPricingRepository) {

        this.itemRepository = itemRepository;
        this.itemPricingRepository = itemPricingRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getInUseItems() {
        return itemRepository.findByState(1);
    }


    public Optional<Item> getItemById(Integer id) {
        return itemRepository.findById(id);
    }

    public Item createItem(ItemDTO itemDTO) {
        Item newItem = new Item();
        newItem.setName(itemDTO.getName());

        // Find Pricing by ID
        Optional<ItemPricing> itemPricingOptional = itemPricingRepository.findById(itemDTO.getPricingId());
        if (itemPricingOptional.isPresent()) {
            ItemPricing itemPricing = itemPricingOptional.get();
            newItem.setItemPricing(itemPricing);
            return itemRepository.save(newItem);
        }
        throw new RuntimeException("ItemPricing Not Found!");
    }

    public void updateItem(Integer id, Item updatedItem) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item existingItem = optionalItem.get();
            existingItem.setName(updatedItem.getName());
            existingItem.setState(updatedItem.getState());
            existingItem.setItemPricing(updatedItem.getItemPricing());
            itemRepository.save(existingItem);
        } else {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
    }

    public void deleteItem(Integer id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            itemRepository.delete(optionalItem.get());
        } else {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
    }

    public void enableItem(Integer id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setEnabled(1);
            itemRepository.save(item);
        } else {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
    }

    public void disableItem(Integer id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setEnabled(0);
            itemRepository.save(item);
        } else {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
    }

    public void useItem(Integer id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setState(1);
            itemRepository.save(item);
        } else {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
    }

    public void freeItem(Integer id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setState(0);
            itemRepository.save(item);
        } else {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
    }
}

