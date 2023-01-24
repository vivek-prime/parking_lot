package paymentStrategy;

import model.PaymentStatus;

public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentStatus makePayment(Long parkingFee) {
        System.out.println("Payment of Rs. " + parkingFee + " done through Cash");
        return PaymentStatus.SUCCESS;
    }
}
