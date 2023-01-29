package paymentStrategy;

import model.PaymentStatus;

public interface PaymentStrategy {
    PaymentStatus makePayment(Double parkingFee);
}
