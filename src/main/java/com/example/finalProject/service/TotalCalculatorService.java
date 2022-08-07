package com.example.finalProject.service;

import com.example.finalProject.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TotalCalculatorService {

    public static final int PVM = 21;

    public Double calculateTotalAmount(List<Product> products) {
        double productAmount = 0;

        for (Product product : products) {
            productAmount += product.getPrice() * product.getQuantity();
        }

        return productAmount;
    }

    public Double calculatePVM(Double amount) {
        return amount * (PVM / 100);
    }
}
