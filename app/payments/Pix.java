package app.payments;

import app.accounts.Buyer;
import app.accounts.Seller;


public class Pix extends PaymentMethod {

    public boolean pay(Buyer buyer, Seller seller, double value) {
        if (buyer.withdraw(value)) {
            return seller.deposit(value);
        }

        return false;
    }

    public String getName() {
        return "Pix";
    }

}
