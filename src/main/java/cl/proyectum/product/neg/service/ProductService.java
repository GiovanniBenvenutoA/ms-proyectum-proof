package cl.proyectum.product.neg.service;

import cl.proyectum.product.neg.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    Product create(Product p);
    Product get(Long id);
    Page<Product> list(String q, Pageable pageable);
    Product update(Long id, Product p);
    void delete(Long id);

}
