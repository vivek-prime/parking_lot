package paymentStrategy;

import model.PaymentStatus;

public class UpiPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentStatus makePayment(Double parkingFee) {
        System.out.println("Payment of Rs. " + parkingFee + " done through UPI");
        return PaymentStatus.SUCCESS;
    }
}