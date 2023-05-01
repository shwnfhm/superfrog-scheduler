package edu.tcu.cs.superfrogscheduler.payment;

import edu.tcu.cs.superfrogscheduler.appearance.AppearanceService;
import edu.tcu.cs.superfrogscheduler.payment.dto.RequestIds;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/payment-forms")
    public Result generatePaymentForms(@RequestBody RequestIds requestIds) {
        List<Long> selectedIds = requestIds.getRequestIds();

        Period paymentPeriod = requestIds.getPaymentPeriod();

        List<PaymentForm> paymentForms = this.paymentService.generatePaymentForms(selectedIds, paymentPeriod);

        for(int i = 0; i<selectedIds.size(); i++){
            this.appearanceService.submitToPayroll(selectedIds.get(i));
        }
        return new Result(true, StatusCode.SUCCESS, "Payment forms are generated successfully.", paymentForms);
    }
}
