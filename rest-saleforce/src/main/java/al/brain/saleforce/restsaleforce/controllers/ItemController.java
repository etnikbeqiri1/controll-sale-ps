package al.brain.saleforce.restsaleforce.controllers;

import al.brain.saleforce.restsaleforce.dtos.ItemDTO;
import al.brain.saleforce.restsaleforce.helpers.ApiResponse;
import al.brain.saleforce.restsaleforce.models.Item;
import al.brain.saleforce.restsaleforce.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Item>>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        ApiResponse<List<Item>> response = new ApiResponse<>(true, "Items retrieved successfully", items, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Item>> getItemById(@PathVariable Integer id) {
        Optional<Item> optionalItem = itemService.getItemById(id);
        ApiResponse<Item> response = optionalItem
                .map(item -> new ApiResponse<>(true, "Item retrieved successfully", item, LocalDateTime.now()))
                .orElse(new ApiResponse<>(false, "Item not found", null, LocalDateTime.now()));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Item>> createItem(@RequestBody ItemDTO itemDTO) {
        Item createdItem = itemService.createItem(itemDTO);
        ApiResponse<Item> response = new ApiResponse<>(true, "Item created successfully", createdItem, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateItem(@PathVariable Integer id, @RequestBody Item updatedItem) {
        itemService.updateItem(id, updatedItem);
        ApiResponse<Void> response = new ApiResponse<>(true, "Item updated successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Integer id) {
        itemService.deleteItem(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Item deleted successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<ApiResponse<Void>> enableItem(@PathVariable Integer id) {
        itemService.enableItem(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Item enabled successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<ApiResponse<Void>> disableItem(@PathVariable Integer id) {
        itemService.disableItem(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Item disabled successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/use")
    public ResponseEntity<ApiResponse<Void>> useItem(@PathVariable Integer id) {
        itemService.useItem(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Item used successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/free")
    public ResponseEntity<ApiResponse<Void>> freeItem(@PathVariable Integer id) {
        itemService.freeItem(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Item freed successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

}
