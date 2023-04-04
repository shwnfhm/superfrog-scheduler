package edu.tcu.cs.superfrogscheduler.payment.dto;

import edu.tcu.cs.superfrogscheduler.payment.Period;

import java.util.List;

public class RequestIds {
    private List<Long> requestIds;

    private Period paymentPeriod;

    public RequestIds() {

    }

    public RequestIds(List<Long> requestIds, Period paymentPeriod) {
        this.requestIds = requestIds;
        this.paymentPeriod = paymentPeriod;
    }

    public List<Long> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<Long> requestIds) {
        this.requestIds = requestIds;
    }

    public Period getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(Period paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }
}
