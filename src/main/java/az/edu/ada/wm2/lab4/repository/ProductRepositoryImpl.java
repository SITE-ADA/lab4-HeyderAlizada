package az.edu.ada.wm2.lab4.repository;

import az.edu.ada.wm2.lab4.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepositoryImpl implements ProductRepository{

    private HashMap<UUID, Product> mapProduct  = new HashMap<>();

    @Override
    public Product save(Product product) {
        if (product.getId()==null){
            product.setId(UUID.randomUUID());
        }
        mapProduct.put(product.getId(), product);
        return  product;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(mapProduct.get(id));
    }

    @Override
    public List<Product> findAll() {
        ArrayList<Product> productList = new ArrayList<>();
        productList.addAll(mapProduct.values());
        return productList;
    }

    @Override
    public void deleteById(UUID id) {
        mapProduct.remove(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return mapProduct.containsKey(id);
    }
}
