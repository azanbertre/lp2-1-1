package app.payments;

import java.time.LocalDate;

import app.accounts.Buyer;
import app.accounts.Seller;


public class Ticket extends PaymentMethod {

    private LocalDate expireDate;
    private double issuePrice;

    public Ticket(LocalDate expireDate, double issuePrice) {
        this.expireDate = expireDate;
        this.issuePrice = issuePrice;
    }

    public boolean pay(Buyer buyer, Seller seller, double value) {
        if (LocalDate.now().isAfter(this.expireDate)) {
            return false;
        }

        if (buyer.withdraw(value)) {
            return seller.deposit(value - this.issuePrice);
        }

        return false;
    }

    public String getName() {
        return "Boleto";
    }

}
