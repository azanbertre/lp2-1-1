package app.payments;

import app.accounts.Buyer;
import app.accounts.Seller;


public class Credit extends PaymentMethod {

    private double rate;

    public Credit(double rate) {
        this.rate = rate;
    }

    public boolean pay(Buyer buyer, Seller seller, double value) {
        buyer.addToPay(value);
        seller.addToReceive(value - (value * this.rate / 100));

        return true;
    }

    public String getName() {
        return "Crédito";
    }

}
