package al.brain.saleforce.restsaleforce.services;

import al.brain.saleforce.restsaleforce.models.Product;
import al.brain.saleforce.restsaleforce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Integer id, Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(updatedProduct.getName());
            product.setInternalName(updatedProduct.getInternalName());
            product.setPrice(updatedProduct.getPrice());
            product.setImage(updatedProduct.getImage());
            product.setStock(updatedProduct.getStock());
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Product> searchProductsByInternalName(String keyword) {
        return productRepository.findByInternalNameContainingIgnoreCase(keyword);
    }

    public List<Product> getProductsByPriceRange(float minPrice, float maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> getProductsByStockRange(int minStock, int maxStock) {
        return productRepository.findByStockBetween(minStock, maxStock);
    }
}

