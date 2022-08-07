package com.example.finalProject.service;

import com.example.finalProject.exception.ProductNotFoundException;
import com.example.finalProject.model.Product;
import com.example.finalProject.model.Supplier;
import com.example.finalProject.repository.ProductRepository;
import com.example.finalProject.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;

    public ProductService(ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<Product> findAllProducts() {
        LOGGER.info("Getting all Products");
        return productRepository.findAll();
    }

    public Product saveProduct(final Product product) {
        LOGGER.info("Saving Product to DB " + product);
        for (int i = 0; i < supplierRepository.findAll().size(); i++) {
            if(supplierRepository.findAll().get(i).getName().equalsIgnoreCase(product.getSupplier().getName())) {
                product.setSupplier(supplierRepository.findAll().get(i));
            }
        }
        productRepository.save(product);
        return product;
    }

    public Product getProductById(final Integer id) throws ProductNotFoundException {
        LOGGER.info("Getting Product information, where id: " + id);

        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not existing with id " + id));
    }

    public void deleteProductById(final Integer id) {
        LOGGER.warn("Deleting Product where product id = " + id);
        productRepository.deleteById(id);
    }

    public Product updateProduct(final Product newProduct, Integer id) {
        for (Supplier supplier : supplierRepository.findAll()) {
            if(supplier.getName().equalsIgnoreCase(newProduct.getSupplier().getName())){
                newProduct.setSupplier(supplier);
            }
        }
        return productRepository.findById(id)
                .map(product -> {
                    LOGGER.info("Updating product where ID = " + id);
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    product.setCategory(newProduct.getCategory());
                    product.setUniqueCode(newProduct.getUniqueCode());
                    product.setMeasureUnit(newProduct.getMeasureUnit());
                    product.setQuantity(newProduct.getQuantity());
                    product.setSupplier(newProduct.getSupplier());
                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    LOGGER.info("Product not existing with id " + id + " Then creating new Product = " + newProduct);
                    return productRepository.save(newProduct);
                });

    }
}
