package paymentStrategy;

import model.PaymentType;

public class PaymentStrategyFactory {
    public static PaymentStrategy getPaymentStrategy(PaymentType paymentType) {
        switch (paymentType) {
            case CASH:
                return new CashPaymentStrategy();
            case UPI:
                return new UpiPaymentStrategy();
        }
        return null;
    }
}
