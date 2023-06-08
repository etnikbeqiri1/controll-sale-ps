package al.brain.saleforce.restsaleforce.controllers;

import al.brain.saleforce.restsaleforce.helpers.ApiResponse;
import al.brain.saleforce.restsaleforce.models.Product;
import al.brain.saleforce.restsaleforce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        ApiResponse<List<Product>> response = new ApiResponse<>(true, "Success", products, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            ApiResponse<Product> response = new ApiResponse<>(true, "Success", optionalProduct.get(), LocalDateTime.now());
            return ResponseEntity.ok(response);
        }
        ApiResponse<Product> response = new ApiResponse<>(false, "Product not found", null, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        ApiResponse<Product> response = new ApiResponse<>(true, "Product created successfully", createdProduct, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        Product product = productService.updateProduct(id, updatedProduct);
        if (product != null) {
            ApiResponse<Product> response = new ApiResponse<>(true, "Product updated successfully", product, LocalDateTime.now());
            return ResponseEntity.ok(response);
        }
        ApiResponse<Product> response = new ApiResponse<>(false, "Product not found", null, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> searchProductsByName(@RequestParam("keyword") String keyword) {
        List<Product> products = productService.searchProductsByName(keyword);
        int count = products.size();
        String message = count > 0 ? "Found " + count + " products" : "No products found";
        ApiResponse<List<Product>> response = new ApiResponse<>(true, message, products, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/searchByInternalName")
    public ResponseEntity<ApiResponse<List<Product>>> searchProductsByInternalName(@RequestParam("keyword") String keyword) {
        List<Product> products = productService.searchProductsByInternalName(keyword);
        ApiResponse<List<Product>> response = new ApiResponse<>(true, "Success", products, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/priceRange")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByPriceRange(@RequestParam("minPrice") float minPrice,
                                                                              @RequestParam("maxPrice") float maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        ApiResponse<List<Product>> response = new ApiResponse<>(true, "Success", products, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stockRange")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByStockRange(@RequestParam("minStock") int minStock,
                                                                              @RequestParam("maxStock") int maxStock) {
        List<Product> products = productService.getProductsByStockRange(minStock, maxStock);
        ApiResponse<List<Product>> response = new ApiResponse<>(true, "Success", products, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

}
