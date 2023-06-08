package al.brain.saleforce.restsaleforce.controllers;

import al.brain.saleforce.restsaleforce.dtos.PricingDTO;
import al.brain.saleforce.restsaleforce.dtos.PricingUpdateDTO;
import al.brain.saleforce.restsaleforce.helpers.ApiResponse;
import al.brain.saleforce.restsaleforce.models.Pricing;
import al.brain.saleforce.restsaleforce.services.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pricing")
public class PricingController {

    private final PricingService pricingService;

    @Autowired
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pricing>>> getAllPricing() {
        List<Pricing> pricingList = pricingService.getAllPricing();
        ApiResponse<List<Pricing>> response = new ApiResponse<>(true, "Pricing retrieved successfully", pricingList, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Pricing>> getPricingById(@PathVariable Integer id) {
        Optional<Pricing> optionalPricing = pricingService.getPricingById(id);
        ApiResponse<Pricing> response = optionalPricing
                .map(pricing -> new ApiResponse<>(true, "Pricing retrieved successfully", pricing, LocalDateTime.now()))
                .orElse(new ApiResponse<>(false, "Pricing not found", null, LocalDateTime.now()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/enabled")
    public ResponseEntity<ApiResponse<List<Pricing>>> getEnabledPricing() {
        List<Pricing> enabledPricingList = pricingService.getEnabledPricing();
        ApiResponse<List<Pricing>> response = new ApiResponse<>(true, "Enabled pricing retrieved successfully", enabledPricingList, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Pricing>> createPricing(@RequestBody PricingDTO pricingDTO) {
        Pricing createdPricing = pricingService.createPricing(pricingDTO);
        ApiResponse<Pricing> response = new ApiResponse<>(true, "Pricing created successfully", createdPricing, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePricing(@PathVariable Integer id, @RequestBody PricingUpdateDTO pricingDTO) {
        pricingService.updatePricing(id, pricingDTO);
        ApiResponse<Void> response = new ApiResponse<>(true, "Pricing updated successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<ApiResponse<Void>> enablePricing(@PathVariable Integer id) {
        pricingService.enablePricing(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Pricing enabled successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<ApiResponse<Void>> disablePricing(@PathVariable Integer id) {
        pricingService.disablePricing(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Pricing disabled successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePricing(@PathVariable Integer id) {
        pricingService.deletePricing(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Pricing deleted successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}

