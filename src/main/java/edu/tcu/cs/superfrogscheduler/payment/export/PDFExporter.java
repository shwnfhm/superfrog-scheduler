package edu.tcu.cs.superfrogscheduler.payment.export;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import edu.tcu.cs.superfrogscheduler.payment.PaymentForm;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import edu.tcu.cs.superfrogscheduler.user.PaymentPreference;
import edu.tcu.cs.superfrogscheduler.user.UserService;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PDFExporter {
    private List<PaymentForm> formList;

    public void generate(HttpServletResponse response) throws DocumentException, IOException {

        int i = 0;
        ArrayList<String> fileNames = new ArrayList<>();
        for(PaymentForm pForm : formList) {
            String filePath = new File("src/main/java/edu/tcu/cs/superfrogscheduler/payment/export/Honorarium_Request_Final.pdf").getAbsolutePath();
            PdfReader reader = new PdfReader(filePath);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(String.format("PDF_File_%d.pdf",i)));
            fileNames.add(String.format("PDF_File_%d.pdf", i));
            AcroFields form = stamper.getAcroFields();
            System.out.println(form.getAllFields());
            form.setField("Name", pForm.getFirstName() + " " + pForm.getLastName());

            String[] checkboxstates = form.getAppearanceStates("Check Box1");
            String[] checkbox2states = form.getAppearanceStates("Check Box2");
            if(pForm.getPaymentPreference() == PaymentPreference.MAILCHECK) {
                form.setField("Check Box1", checkboxstates[1]);
            }
            else{
                form.setField("Check Box2", checkbox2states[1]);
            }
            String[] checkbox12states = form.getAppearanceStates("Check Box12");
            if(pForm.isInternational() == true){
                form.setField("Check Box12", checkbox12states[1]);
            }
            else{
                form.setField("Check Box12", checkbox12states[0]);
            }

            form.setField("Amount", pForm.getAmount().toString());
            form.setField("Permanentaddress 2", pForm.getAddress());
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
            i++;
            //PdfContentByte cb = writer.getDirectContent();
            //PdfReader reader2 = new PdfReader(out.toByteArray());
            //PdfImportedPage page = writer.getImportedPage(reader2, 1);
            //document.newPage();
        }
        PDFMergerUtility obj = new PDFMergerUtility();
        obj.setDestinationFileName("finalforms.pdf");
        for(int j = 0; j<fileNames.size(); j++){
            obj.addSource(new File(fileNames.get(j)));
        }
        obj.mergeDocuments();
        FileInputStream fileInputStream = new FileInputStream("finalforms.pdf");
        int bytes;
        while((bytes = fileInputStream.read()) != -1){
            response.getOutputStream().write(bytes);
        }
    }

    public void setFormList(List<PaymentForm> paymentForms) {
        this.formList = paymentForms;
    }
}
