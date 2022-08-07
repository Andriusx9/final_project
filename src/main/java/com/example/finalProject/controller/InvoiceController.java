package com.example.finalProject.controller;

import com.example.finalProject.exception.ClientNotFoundException;
import com.example.finalProject.exception.InvoiceNotFoundException;
import com.example.finalProject.model.Client;
import com.example.finalProject.model.Invoice;
import com.example.finalProject.repository.InvoiceRepository;
import com.example.finalProject.repository.ProductRepository;
import com.example.finalProject.service.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {

    private static Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);

    private final InvoiceRepository invoiceRepository;
    private final ClientService clientService;
    private final InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.findAllInvoices();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<Invoice> generatePdf(@PathVariable("id") Integer id) throws InvoiceNotFoundException {
        Invoice invoice = invoiceService.getInvoiceById(id);
        CreatePDF.createPDF(invoice);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") Integer id) throws InvoiceNotFoundException {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @PostMapping("/add-invoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody @Valid Invoice invoice) {
        Invoice newInvoice = invoiceService.saveInvoice(invoice);
        CreatePDF.createPDF(invoice);
        return new ResponseEntity<>(newInvoice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public void updateInvoice(@RequestBody Invoice newInvoice, @PathVariable("id") Integer id) {
        LOGGER.info("Updating invoice " + newInvoice);
        newInvoice.setCreationTime(LocalDateTime.now());
        invoiceRepository.save(newInvoice);

    }

    @GetMapping("/{iid}/{cid}")
    public ResponseEntity<Invoice> addClientToInvoice(@PathVariable("iid") Integer iid, @PathVariable("pid") Integer cid) throws ClientNotFoundException {
        LOGGER.info("Updating invoice with new product");
        Invoice invoice = invoiceRepository.findById(iid).get();
        Client client = clientService.getClientById(cid);

        invoice.setClient(client);

        invoiceRepository.save(invoice);

        return new ResponseEntity<>(invoice, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public void deleteInvoiceById(@PathVariable("id") Integer id) {
       invoiceService.deleteInvoiceById(id);
    }

}
