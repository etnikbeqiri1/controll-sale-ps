package al.brain.saleforce.restsaleforce.services;

import al.brain.saleforce.restsaleforce.dtos.PricingDTO;
import al.brain.saleforce.restsaleforce.dtos.PricingUpdateDTO;
import al.brain.saleforce.restsaleforce.exceptions.PricingNotFoundException;
import al.brain.saleforce.restsaleforce.models.ItemPricing;
import al.brain.saleforce.restsaleforce.models.Pricing;
import al.brain.saleforce.restsaleforce.repositories.ItemPricingRepository;
import al.brain.saleforce.restsaleforce.repositories.PricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PricingService {

    private final PricingRepository pricingRepository;
    private final ItemPricingRepository itemPricingRepository;

    @Autowired
    public PricingService(PricingRepository pricingRepository, ItemPricingRepository itemPricingRepository) {

        this.pricingRepository = pricingRepository;
        this.itemPricingRepository = itemPricingRepository;
    }

    public List<Pricing> getAllPricing() {
        return pricingRepository.findAll();
    }

    public Optional<Pricing> getPricingById(Integer id) {
        return pricingRepository.findById(id);
    }

    public List<Pricing> getEnabledPricing() {
        return pricingRepository.findByEnabled(1);
    }

    public Pricing createPricing(PricingDTO pricingDTO) {
        Pricing pricing = new Pricing();
        pricing.setName(pricingDTO.getName());
        pricing.setPrice(pricingDTO.getPrice());
        pricing.setStartPrice(pricingDTO.getStartPrice());
        pricing.setEnabled(pricingDTO.getEnabled());
        // Find ItemPricing by ID
        Optional<ItemPricing> itemPricingOptional = itemPricingRepository.findById(pricingDTO.getItemPricing());
        if (itemPricingOptional.isPresent()) {
            ItemPricing itemPricing = itemPricingOptional.get();
            pricing.setItemPricing(itemPricing);
            return pricingRepository.save(pricing);
        }
        throw new RuntimeException("Item Not Found!");
    }

    public void updatePricing(Integer id, PricingUpdateDTO updatedPricing) {
        Optional<Pricing> optionalPricing = pricingRepository.findById(id);
        if (optionalPricing.isPresent()) {
            Pricing existingPricing = optionalPricing.get();
            existingPricing.setName(updatedPricing.getName());
            existingPricing.setPrice(updatedPricing.getPrice());
            existingPricing.setStartPrice(updatedPricing.getStartPrice());
            existingPricing.setEnabled(updatedPricing.getEnabled());
            pricingRepository.save(existingPricing);
        } else {
            throw new PricingNotFoundException("Pricing not found with id: " + id);
        }
    }

    public void enablePricing(Integer id) {
        Optional<Pricing> optionalPricing = pricingRepository.findById(id);
        if (optionalPricing.isPresent()) {
            Pricing pricing = optionalPricing.get();
            pricing.setEnabled(1);
            pricingRepository.save(pricing);
        } else {
            throw new PricingNotFoundException("Pricing not found with id: " + id);
        }
    }

    public void disablePricing(Integer id) {
        Optional<Pricing> optionalPricing = pricingRepository.findById(id);
        if (optionalPricing.isPresent()) {
            Pricing pricing = optionalPricing.get();
            pricing.setEnabled(0);
            pricingRepository.save(pricing);
        } else {
            throw new PricingNotFoundException("Pricing not found with id: " + id);
        }
    }

    public void deletePricing(Integer id) {
        Optional<Pricing> optionalPricing = pricingRepository.findById(id);
        if (optionalPricing.isPresent()) {
            pricingRepository.delete(optionalPricing.get());
        } else {
            throw new PricingNotFoundException("Pricing not found with id: " + id);
        }
    }
}

