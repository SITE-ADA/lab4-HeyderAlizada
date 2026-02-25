package az.edu.ada.wm2.lab4.service;

import az.edu.ada.wm2.lab4.model.Product;
import az.edu.ada.wm2.lab4.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {


    public final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {

        if (product.getId() == null) {
            product.setId(UUID.randomUUID());
        }

        productRepository.save(product);

        return product;
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();

    }

    @Override
    public Product updateProduct(UUID id, Product product) {

        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        product.setId(id);
        return productRepository.save(product);

    }

    @Override
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductsExpiringBefore(LocalDate date) {

        ArrayList<Product> expList = new ArrayList<>();

        for(Product product : productRepository.findAll()){
            if(product.getExpirationDate() != null && product.getExpirationDate().isBefore(date)){
                expList.add(product);
            }
        }
        return  expList;
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {

        ArrayList<Product> priceProduct = new ArrayList<>();

        for(Product product : productRepository.findAll()){
            if (product.getPrice() != null
                    && product.getPrice().compareTo(minPrice) >= 0
                    && product.getPrice().compareTo(maxPrice) <= 0) {

                priceProduct.add(product);

            }
        }
    return priceProduct;
    }
}
