package al.brain.saleforce.restsaleforce.controllers;

import al.brain.saleforce.restsaleforce.dtos.ItemPricingDTO;
import al.brain.saleforce.restsaleforce.dtos.ItemPricingUpdateDTO;
import al.brain.saleforce.restsaleforce.helpers.ApiResponse;
import al.brain.saleforce.restsaleforce.models.ItemPricing;
import al.brain.saleforce.restsaleforce.services.ItemPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/item-pricing")
public class ItemPricingController {

    private final ItemPricingService itemPricingService;

    @Autowired
    public ItemPricingController(ItemPricingService itemPricingService) {
        this.itemPricingService = itemPricingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ItemPricing>>> getAllItemPricing() {
        List<ItemPricing> itemPricingList = itemPricingService.getAllItemPricing();
        ApiResponse<List<ItemPricing>> response = new ApiResponse<>(true, "Item pricing retrieved successfully", itemPricingList, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/enabled")
    public ResponseEntity<ApiResponse<List<ItemPricing>>> getEnabledItemPricing() {
        List<ItemPricing> enabledItemPricingList = itemPricingService.getEnabledItemPricing();
        ApiResponse<List<ItemPricing>> response = new ApiResponse<>(true, "Enabled item pricing retrieved successfully", enabledItemPricingList, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/disabled")
    public ResponseEntity<ApiResponse<List<ItemPricing>>> getDisabledItemPricing() {
        List<ItemPricing> disabledItemPricingList = itemPricingService.getDisabledItemPricing();
        ApiResponse<List<ItemPricing>> response = new ApiResponse<>(true, "Disabled item pricing retrieved successfully", disabledItemPricingList, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemPricing>> getItemPricingById(@PathVariable Integer id) {
        Optional<ItemPricing> optionalItemPricing = itemPricingService.getItemPricingById(id);
        ApiResponse<ItemPricing> response = optionalItemPricing
                .map(itemPricing -> new ApiResponse<>(true, "Item pricing retrieved successfully", itemPricing, LocalDateTime.now()))
                .orElse(new ApiResponse<>(false, "Item pricing not found", null, LocalDateTime.now()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<ApiResponse<Void>> enableItemPricing(@PathVariable Integer id) {
        itemPricingService.enableItemPricing(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Item pricing enabled successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<ApiResponse<Void>> disableItemPricing(@PathVariable Integer id) {
        itemPricingService.disableItemPricing(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Item pricing disabled successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ItemPricingDTO>> createItemPricing(@RequestBody ItemPricingDTO itemPricingDTO) {
        ItemPricingDTO createdItemPricing = itemPricingService.createItemPricing(itemPricingDTO);
        ApiResponse<ItemPricingDTO> response = new ApiResponse<>(true, "Item pricing created successfully", createdItemPricing, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateItemPricing(@PathVariable Integer id, @RequestBody ItemPricingUpdateDTO updatedItemPricingDTO) {
        itemPricingService.updateItemPricing(id, updatedItemPricingDTO);

        ApiResponse<Void> response = new ApiResponse<>(true, "Item pricing updated successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Integer id) {
        itemPricingService.deleteItemPricing(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Item pricing deleted successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
