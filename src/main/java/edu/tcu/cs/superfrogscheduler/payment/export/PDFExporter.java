package edu.tcu.cs.superfrogscheduler.payment.export;

import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import edu.tcu.cs.superfrogscheduler.payment.PaymentForm;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class PDFExporter {
    private List<PaymentForm> formList;

    public void generate(HttpServletResponse response) throws DocumentException, IOException {

        // Creating the Object of Document
        Document document = new Document(PageSize.A4);

        // Getting instance of PdfWriter
        PdfWriter.getInstance(document, response.getOutputStream());

        // Opening the created document to modify it
        document.open();

        // Creating font
        // Setting font style and size
        Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTiltle.setSize(20);

        // Creating paragraph
        Paragraph paragraph = new Paragraph("Payment Forms", fontTiltle);

        // Aligning the paragraph in document
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        // Adding the created paragraph in document
        document.add(paragraph);

        // Creating a table of 6 columns
        PdfPTable table = new PdfPTable(6);

        // Setting width of table, its columns and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 3, 3, 3, 3, 3, 3});
        table.setSpacingBefore(5);

        // Create Table Cells for table header
        PdfPCell cell = new PdfPCell();

        // Setting the background color and padding
        cell.setBackgroundColor(CMYKColor.MAGENTA);
        cell.setPadding(5);

        // Creating font
        // Setting font style and size
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);

        // Adding headings in the created table cell/ header
        // Adding Cell to table
        cell.setPhrase(new Phrase("First Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Last Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Superfrog ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Payment Period Start", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Payment Period End", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        // Iterating over the list of payment forms
        for (PaymentForm form : formList) {

            table.addCell(String.valueOf(form.getFirstName()));

            table.addCell(form.getLastName());

            table.addCell(form.getStudentId().toString());
            table.addCell(form.getPaymentPeriod().getBeginDate().toString());
            table.addCell(form.getPaymentPeriod().getEndDate().toString());
            table.addCell(form.getAmount().toString());
        }

        document.add(table);


        document.close();

    }

    public void setFormList(List<PaymentForm> paymentForms) {
        this.formList = paymentForms;
    }
}
