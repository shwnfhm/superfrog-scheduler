package edu.tcu.cs.superfrogscheduler.payment.export;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import edu.tcu.cs.superfrogscheduler.payment.PaymentForm;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import edu.tcu.cs.superfrogscheduler.user.PaymentPreference;
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
            if(pForm.getPaymentPreference() == PaymentPreference.MAILCHECK) {
                form.setField("Check Box1", checkboxstates[1]);
            }
            else{
                form.setField("Check Box1", checkboxstates[0]);
            }
            String[] checkbox12states = form.getAppearanceStates("Check Box12");
            if(pForm.isInternational() == true){
                form.setField("Check Box12", checkbox12states[1]);
            }
            else{
                form.setField("Check Box12", checkbox12states[0]);
            }
            form.setField("Check Box3", form.getAppearanceStates("Check Box3")[1]);
            form.setField("Amount", pForm.getAmount().toString());
            form.setField("Permanentaddress 2", pForm.getAddress());
            form.setField("Permanentaddress 1", "Fort Worth, TX 76129");
            form.setField("1 Attach a copy of written agreement or explain the nature and DATE OF SERVICES performed 1", "For SuperFrog appearances made between the dates "
                    + pForm.getPaymentPeriod().getBeginDate().toString() + " and "
                    + pForm.getPaymentPeriod().getEndDate().toString() + "." );
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
            i++;
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
