package edu.tcu.cs.superfrogscheduler.payment;

import com.lowagie.text.DocumentException;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceService;
import edu.tcu.cs.superfrogscheduler.payment.dto.RequestIds;
import edu.tcu.cs.superfrogscheduler.payment.export.PDFExporter;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/payment")
public class PaymentController {
    private PaymentService paymentService;

    private AppearanceService appearanceService;

    public PaymentController(PaymentService paymentService, AppearanceService appearanceService) {
        this.paymentService = paymentService;
        this.appearanceService = appearanceService;
    }

    @GetMapping("/payment-forms")
    public Result getAllPaymentForms(){
        return new Result(true, StatusCode.SUCCESS, "Get Payment Forms Successful", this.paymentService.getPaymentForms());
    }

    @PostMapping("/payment-forms")
    public void generatePaymentForms(@RequestBody RequestIds requestIds, HttpServletResponse response) throws DocumentException, IOException {
        List<Long> selectedIds = requestIds.getRequestIds();

        Period paymentPeriod = requestIds.getPaymentPeriod();

        List<PaymentForm> paymentForms = this.paymentService.generatePaymentForms(selectedIds, paymentPeriod);

        for(int i = 0; i<selectedIds.size(); i++){
            this.appearanceService.submitToPayroll(selectedIds.get(i));
        }

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=paymentform_" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headervalue);

        PDFExporter exporter = new PDFExporter();
        exporter.setFormList(paymentForms);
        exporter.generate(response);
    }

}
