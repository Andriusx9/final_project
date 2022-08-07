package com.example.finalProject.service;

import com.example.finalProject.model.Invoice;
import com.example.finalProject.model.Product;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class CreatePDF {

    private static Logger LOGGER = LoggerFactory.getLogger(CreatePDF.class);
    @Autowired
    private static TotalCalculatorService totalCalculatorService;

    public static void createPDF(Invoice invoice) {
        String patch = "C:/Users/Namai/Desktop/Andriaus/Java/Final Project/finalProjectFrontEnd/final-project-invoice/src/assets/invoice.pdf";

        PdfWriter pdfWriter = null;

        try {
            pdfWriter = new PdfWriter(patch);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        assert pdfWriter != null;
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        try {
            PdfFont pdfFontWithLtLetters = PdfFontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.CP1257);

            float column = 280f;
            float[] columnWidth = {column, column};
            Table table = new Table(columnWidth);

            table.setBackgroundColor(new DeviceRgb(63, 169, 219))
                    .setFontColor(Color.WHITE);
            table.addCell(new Cell().add("Sąskaita faktūra")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setMarginTop(30f)
                    .setMarginBottom(30f)
                    .setFontSize(30f)
                    .setBorder(Border.NO_BORDER)
                    .setFont(pdfFontWithLtLetters)
            );
            table.addCell(new Cell().add("UAB Pasakų šalis\nLT-52158 Pasvalys\n+37068127600")
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(30f)
                    .setMarginBottom(30f)
                    .setBorder(Border.NO_BORDER)
                    .setMarginRight(20f)
                    .setFont(pdfFontWithLtLetters)

            );

            Double orderAmount = invoice.getPrice();

            float[] secondColumnWidth = {80, 300, 100, 80};
            Table customerInfoTable = new Table(secondColumnWidth);

            customerInfoTable.addCell(new Cell(0, 4)
                    .add("Pirkėjo informacija")
                    .setBorder(Border.NO_BORDER)
                    .setBackgroundColor(new DeviceRgb(63, 169, 219))
                    .setFontColor(Color.WHITE)
                    .setFont(pdfFontWithLtLetters)
            );

            customerInfoTable.addCell(new Cell().add("Pavadinimas:").setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER));
            customerInfoTable.addCell(new Cell().add(invoice.getClient().getName()).setBorder(Border.NO_BORDER));//customerFullName

            customerInfoTable.addCell(new Cell().add("Sąskaitos nr.:").setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(pdfFontWithLtLetters));
            customerInfoTable.addCell(new Cell().add(String.valueOf(invoice.getSerialNo())).setBorder(Border.NO_BORDER)//orderId
                    .setTextAlignment(TextAlignment.CENTER));

            customerInfoTable.addCell(new Cell().add("Adresas:").setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER));
            customerInfoTable.addCell(new Cell().add(invoice.getClient().getAddress()).setBorder(Border.NO_BORDER)//customerAddress
                    .setFont(pdfFontWithLtLetters));

            customerInfoTable.addCell(new Cell().add("Įmonės kodas:").setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFont(pdfFontWithLtLetters));

            customerInfoTable.addCell(new Cell().add(String.valueOf(invoice.getClient().getCompanyCode())).setBorder(Border.NO_BORDER)//customerPersonalCode
                    .setTextAlignment(TextAlignment.CENTER));

            customerInfoTable.addCell(new Cell().add("Tel. Nr.:").setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER));
            customerInfoTable.addCell(new Cell().add(invoice.getClient().getPhoneNumber()).setBorder(Border.NO_BORDER));//customerPhoneNumber

            customerInfoTable.addCell(new Cell().add("Data:").setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER));
            customerInfoTable.addCell(new Cell().add(String.valueOf(invoice.getCreationTime().toLocalDate())).setBorder(Border.NO_BORDER)//orderDate
                    .setTextAlignment(TextAlignment.CENTER));

            float[] productsInfoColumnWidth = {40, 270, 70, 90, 90};
            Table productInfoTable = new Table(productsInfoColumnWidth);

            productInfoTable.addCell(new Cell()
                    .add("Nr.")
                    .setBackgroundColor(new DeviceRgb(63, 169, 219))
                    .setFontColor(Color.WHITE)
                    .setTextAlignment(TextAlignment.CENTER));
            productInfoTable.addCell(new Cell().add("Pavadinimas.")
                    .setBackgroundColor(new DeviceRgb(63, 169, 219))
                    .setFontColor(Color.WHITE)
                    .setTextAlignment(TextAlignment.CENTER));
            productInfoTable.addCell(new Cell().add("Mato vnt.")
                    .setBackgroundColor(new DeviceRgb(63, 169, 219))
                    .setFontColor(Color.WHITE)
                    .setTextAlignment(TextAlignment.CENTER));
            productInfoTable.addCell(new Cell().add("Kiekis")
                    .setBackgroundColor(new DeviceRgb(63, 169, 219))
                    .setFontColor(Color.WHITE)
                    .setTextAlignment(TextAlignment.CENTER));
            productInfoTable.addCell(new Cell().add("kaina")
                    .setBackgroundColor(new DeviceRgb(63, 169, 219))
                    .setFontColor(Color.WHITE)
                    .setTextAlignment(TextAlignment.CENTER));

            List<Product> orderProducts = invoice.getProducts();

            for (int i = 0; i < orderProducts.size(); i++) {
                int rowNumber = i + 1;
                String productName = orderProducts.get(i).getName();
                Integer productQuantity = orderProducts.get(i).getQuantity();
                Double productUnitPrice = orderProducts.get(i).getPrice();
                String measureUnit = orderProducts.get(i).getMeasureUnit();

                productInfoTable.addCell(new Cell().add(String.valueOf(rowNumber))
                        .setTextAlignment(TextAlignment.CENTER));
                productInfoTable.addCell(new Cell().add(productName)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(pdfFontWithLtLetters));
                productInfoTable.addCell(new Cell().add(measureUnit)
                        .setTextAlignment(TextAlignment.CENTER));
                productInfoTable.addCell(new Cell().add(String.valueOf(productQuantity))
                        .setTextAlignment(TextAlignment.CENTER));
                productInfoTable.addCell(new Cell().add(String.valueOf(productUnitPrice))
                        .setTextAlignment(TextAlignment.CENTER));

            }

            Double pvmFromOrderAmount = orderAmount * 0.21;

            pvmFromOrderAmount = Math.floor(pvmFromOrderAmount * 100) / 100;

            Double amountWithPVM = orderAmount + pvmFromOrderAmount;
            amountWithPVM = Math.floor(amountWithPVM * 100) / 100;

            productInfoTable.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            productInfoTable.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            productInfoTable.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            productInfoTable.addCell(new Cell().add("Suma be PVM"));
            productInfoTable.addCell(new Cell().add(String.valueOf(orderAmount))
                    .setTextAlignment(TextAlignment.CENTER));

            productInfoTable.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            productInfoTable.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            productInfoTable.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            productInfoTable.addCell(new Cell().add("PVM"));
            productInfoTable.addCell(new Cell().add(String.valueOf(pvmFromOrderAmount))
                    .setTextAlignment(TextAlignment.CENTER));

            productInfoTable.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            productInfoTable.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            productInfoTable.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            productInfoTable.addCell(new Cell().add("Viso"));
            productInfoTable.addCell(new Cell().add(String.valueOf(amountWithPVM))
                    .setTextAlignment(TextAlignment.CENTER));

            float[] amountInWordsColumnWidth = {100, 460};
            Table amountInWords = new Table(amountInWordsColumnWidth);

            amountInWords.addCell(new Cell().add("Suma žodžiais: ")
                    .setFont(pdfFontWithLtLetters)
                    .setBorder(Border.NO_BORDER));
            amountInWords.addCell(new Cell().add(NumbersToWords.numbersToWordsWithEuros(amountWithPVM))
                    .setBorder(Border.NO_BORDER)
                    .setFont(pdfFontWithLtLetters));

            amountInWords.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            amountInWords.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));

            amountInWords.addCell(new Cell().add("Sąskaitą išrašė: ")
                    .setBorder(Border.NO_BORDER)
                    .setFont(pdfFontWithLtLetters));
            amountInWords.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER)

            );

            amountInWords.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));
            amountInWords.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));

            amountInWords.addCell(new Cell().add("Sąskaitą priėmė: ")
                    .setBorder(Border.NO_BORDER)
                    .setFont(pdfFontWithLtLetters));
            amountInWords.addCell(new Cell().add("")
                    .setBorder(Border.NO_BORDER));

            document.add(table);
            document.add(new Paragraph("\n"));
            document.add(customerInfoTable);
            document.add(new Paragraph("\n"));
            document.add(productInfoTable);
            document.add(new Paragraph("\n"));
            document.add(amountInWords);
            document.close();

            LOGGER.info("Pdf was created");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}