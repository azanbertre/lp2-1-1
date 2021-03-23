package app.payments;

import app.accounts.Buyer;
import app.accounts.Seller;


public class Debit extends PaymentMethod {

    private double rate;

    public Debit(double rate) {
        this.rate = rate;
    }

    public boolean pay(Buyer buyer, Seller seller, double value) {
        if (buyer.withdraw(value)) {
            return seller.deposit(value - (value * this.rate / 100));
        }

        return false;
    }

    public String getName() {
        return "Débito";
    }

}
