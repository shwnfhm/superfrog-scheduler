package edu.tcu.cs.superfrogscheduler.payment;

import edu.tcu.cs.superfrogscheduler.appearance.Appearance;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceRepository;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private AppearanceRepository appearanceRepository;

    private PaymentFormRepository paymentFormRepository;

    private UserRepository userRepository;


    public PaymentService(AppearanceRepository requestRepository, PaymentFormRepository paymentFormRepository, UserRepository userRepository) {
        this.appearanceRepository = requestRepository;
        this.paymentFormRepository = paymentFormRepository;
        this.userRepository = userRepository;
    }


    public List<PaymentForm> generatePaymentForms(List<Long> appearanceRequestIdList, Period paymentPeriod) {
        List<Appearance> selectedRequests = this.appearanceRepository.findByRequestIdIn(appearanceRequestIdList);

        Map<User, List<Appearance>> studentRequestsMap = groupRequestsBySuperFrogStudent(selectedRequests);

        // For each SuperFrogStudent, generate a payment form, and then collect the payment forms into a list.
        List<PaymentForm> paymentForms = studentRequestsMap.entrySet().stream()
                .map(entry -> entry.getKey().generatePaymentForm(entry.getValue(), paymentPeriod))
                .collect(Collectors.toList());

        // Persist the generated payment forms and then return them.
        return this.paymentFormRepository.saveAll(paymentForms);
    }

    public List<PaymentForm> getPaymentForms(){
        return this.paymentFormRepository.findAll();
    }

    /**
     * Group the given requests by SuperFrogStudent who has finished this request.
     * The result is a Map<SuperFrogStudent, List<SuperFrogAppearanceRequest>>.
     * For example:
     *  Jane Smith -> request 5, request 6, request 12
     *  John Doe -> request 16, request 17, request 20
     *  Jane Smith -> request 22
     * @param selectedRequests A list of integer request ids.
     * @return A map that maps SuperFrogStudent to her requests
     */
    private Map<User, List<Appearance>> groupRequestsBySuperFrogStudent(List<Appearance> selectedRequests) {
        Map<User, List<Appearance>> studentRequestsMap = selectedRequests.stream()
                .collect(Collectors.groupingBy(Appearance::getAssignedSuperFrog));
        return studentRequestsMap;
    }

}
