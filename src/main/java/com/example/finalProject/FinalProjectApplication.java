package com.example.finalProject;

import com.example.finalProject.model.Client;
import com.example.finalProject.model.Invoice;
import com.example.finalProject.model.Product;
import com.example.finalProject.model.Supplier;
import com.example.finalProject.repository.ClientRepository;
import com.example.finalProject.repository.InvoiceRepository;
import com.example.finalProject.repository.ProductRepository;
import com.example.finalProject.service.InvoiceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class FinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(final InvoiceRepository invoiceRepository,
											   final ClientRepository clientRepository,
											   final ProductRepository productRepository,
											   final InvoiceService invoiceService) {
		return args -> {

			Invoice invoice = new Invoice();
			invoice.setSerialNo("AC12345");
			invoice.setCreationTime(LocalDateTime.now());

			Client client = new Client();
			client.setName("Ukininkas Petras Petraitis");
			client.setAddress("Ukininku g. 125, Kaunas");
			client.setCompanyCode("123456789");
			client.setPvmCode("LT987654321");
			client.setPhoneNumber("+37068127601");
			client.setEmail("ukininkas@gmail.com");

			invoice.setClient(client);

			Product toner = new Product();
			toner.setCategory("Toneriai");
			toner.setPrice(11.5);
			toner.setName("HP 103A Neverstop Toner Reload Kit");
			toner.setQuantity(3);
			toner.setMeasureUnit("VNT");
			toner.setUniqueCode("1");

			Supplier supplier = Supplier.builder()
							.name("Asus")
									.build();

			toner.setSupplier(supplier);

			Product printer = new Product();
			printer.setCategory("Spausdintuvai");
			printer.setPrice(189.99);
			printer.setName("Daugiafunkcis spausdintuvas HP LaserJet MFP M140WE Mono, lazerinis");
			printer.setQuantity(1);
			printer.setMeasureUnit("VNT");
			printer.setUniqueCode("2");
			printer.setSupplier(supplier);

			invoice.setProducts(List.of(toner, printer));

			invoice.setPrice(toner.getPrice()+ printer.getPrice());

			invoiceRepository.save(invoice);



		};
	}

}
