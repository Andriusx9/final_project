package com.example.finalProject.controller;

import com.example.finalProject.exception.SupplierNotFoundException;
import com.example.finalProject.model.Supplier;
import com.example.finalProject.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.findAllSuppliers();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable("id") Integer id) throws SupplierNotFoundException {
        Supplier supplier = supplierService.getSupplierById(id);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @PostMapping("/add-supplier")
    public ResponseEntity<Supplier> createSupplier(@RequestBody @Valid Supplier supplier) {
        Supplier newSupplier = supplierService.saveSupplier(supplier);
        return new ResponseEntity<>(newSupplier, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteSupplier(@PathVariable("id") Integer id) {
        supplierService.deleteSupplierById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@RequestBody @Valid Supplier supplier,@PathVariable Integer id) {
        Supplier updateSupplier = supplierService.updateSupplier(supplier, id);
        return new ResponseEntity<>(updateSupplier, HttpStatus.OK);
    }
}
