package com.example.finalProject.service;

import com.example.finalProject.exception.InvoiceNotFoundException;
import com.example.finalProject.model.Invoice;
import com.example.finalProject.model.Product;
import com.example.finalProject.repository.ClientRepository;
import com.example.finalProject.repository.InvoiceRepository;
import com.example.finalProject.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    public static final String SERIAL_NUMBER_LETTERS = "AC";
    private static Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public List<Invoice> findAllInvoices() {
        LOGGER.info("Getting all Invoices");
        return invoiceRepository.findAll();
    }

    public Invoice saveInvoice(final Invoice invoice) {
        LOGGER.info("Saving Invoice to DB " + invoice);
        invoice.setCreationTime(LocalDateTime.now());

        for (int i = 0; i < clientRepository.findAll().size(); i++) {
            if (clientRepository.findAll().get(i).getCompanyCode().equalsIgnoreCase(invoice.getClient().getCompanyCode())) {
                invoice.setClient(clientRepository.findAll().get(i));
            }
        }

        List<Product> newList = new ArrayList<>();

        boolean isAccess = false;

        for (int i = 0; i < invoice.getProducts().size(); i++) {
            for (Product product : productRepository.findAll()) {
                if(invoice.getProducts().get(i).getUniqueCode().equalsIgnoreCase(product.getUniqueCode())){
                    newList.add(product);
                    isAccess = true;
                    break;
                } else {
                    isAccess = false;
                }
            }
            if(!isAccess){
                newList.add(invoice.getProducts().get(i));
            }
        }

        invoice.setProducts(newList);
        invoiceRepository.save(invoice);
        return invoice;
    }

    public Invoice getInvoiceById(final Integer id) throws InvoiceNotFoundException {
        LOGGER.info("Getting Invoice information, where id: " + id);

        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            return  invoiceRepository.findById(id).orElseThrow();
        } else {
            throw new InvoiceNotFoundException("Invoice not existing with id " + id);
        }

    }

    public void deleteInvoiceById(final Integer id) {
        LOGGER.warn("Deleting Invoice where invoice id = " + id);
        invoiceRepository.deleteById(id);
    }

    public String serialNumberGenerator(String lastInvoiceSerialNumber) {
        if(lastInvoiceSerialNumber == null) {
            String firstTwoLetters = "AA";
            String secondFiveDigits = "00001";

            String fullSerialNumber = firstTwoLetters.concat(secondFiveDigits);

            return fullSerialNumber;

        } else {

            String firstTwoLetters = lastInvoiceSerialNumber.substring(0, 2);
            String secondFiveDigits = lastInvoiceSerialNumber.substring(2, 7);

            int DigitsInteger = Integer.parseInt(secondFiveDigits);

            String newSerialNumber = "";
            String newSerialNumberDigits;
            String newSerialNumberLetters = "";

            if(Integer.parseInt(secondFiveDigits) < 99999) {
                DigitsInteger = DigitsInteger + 1;
            } else {
                DigitsInteger = 1;
                newSerialNumberLetters = "AB";
            }

            newSerialNumberDigits = String.format("%05d" , DigitsInteger );
            newSerialNumber = newSerialNumber.concat(newSerialNumberLetters.concat(newSerialNumberDigits));

            return newSerialNumber;

        }

    }

}



