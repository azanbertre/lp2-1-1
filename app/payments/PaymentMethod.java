package app.payments;

import app.accounts.Buyer;
import app.accounts.Seller;


public abstract class PaymentMethod {

    public PaymentMethod() {  }

    public boolean pay(Buyer buyer, Seller seller, double value) {
        return true;
    }

    public String getName() {
        return "Payment Method";
    }

}
