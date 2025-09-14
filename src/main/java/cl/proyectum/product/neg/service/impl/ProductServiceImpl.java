package cl.proyectum.product.neg.service.impl;

import cl.proyectum.product.neg.entities.Product;
import cl.proyectum.product.neg.exceptions.NotFoundException;
import cl.proyectum.product.neg.repository.ProductRepository;
import cl.proyectum.product.neg.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Product create(Product p) {
        p.setId(null);
        return repo.save(p);
    }

    @Override
    @Transactional(readOnly = true)
    public Product get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto " + id + " no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> list(String q, Pageable pageable) {
        if (q == null || q.isBlank()) {
            return repo.findAll(pageable);
        }
        return repo.findByNameContainingIgnoreCase(q.trim(), pageable);
    }

    @Override
    public Product update(Long id, Product p) {
        Product current = get(id);
        current.setNameProduct(p.getNameProduct());
        current.setPriceProduct(p.getPriceProduct());
        current.setStockProduct(p.getStockProduct());
        current.setDescriptionProduct(p.getDescriptionProduct());
        return repo.save(current);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Producto " + id + " no encontrado");
        }
        repo.deleteById(id);
    }
}
