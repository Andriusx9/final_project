package com.example.finalProject.service;

import com.example.finalProject.exception.SupplierNotFoundException;
import com.example.finalProject.model.Supplier;
import com.example.finalProject.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private static Logger LOGGER = LoggerFactory.getLogger(SupplierService.class);

    private SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> findAllSuppliers() {
        LOGGER.info("Getting all Suppliers");
        return supplierRepository.findAll();
    }

    public Supplier saveSupplier(final Supplier supplier) {
        LOGGER.info("Saving Supplier to DB " + supplier);
        supplierRepository.save(supplier);
        return supplier;
    }

    public Supplier getSupplierById(final Integer id) throws SupplierNotFoundException {
        LOGGER.info("Getting Supplier information, where id: " + id);

        return supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not existing with id " + id));
    }

    public void deleteSupplierById(final Integer id) {
        LOGGER.warn("Deleting Supplier where supplier id = " + id);
        supplierRepository.deleteById(id);
    }

    public Supplier updateSupplier(final Supplier newSupplier, Integer id) {
        return supplierRepository.findById(id)
                .map(supplier -> {
                    LOGGER.info("Updating Supplier Where id = " + id);
                    supplier.setName(newSupplier.getName());
                    return supplierRepository.save(supplier);
                })
                .orElseGet(() -> {
                    LOGGER.info("Supplier not existing with id " + id + " Then creating new Supplier = " + newSupplier);
                    return supplierRepository.save(newSupplier);
                });
    }
}
