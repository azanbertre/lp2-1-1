package app.accounts;

import java.util.ArrayList;

import app.Purchase;


public class Buyer extends Account {

    private ArrayList<Double> toPay;
    private ArrayList<Purchase> purchases;

    public Buyer(String name, double balance, String document) {
        super(name, balance, document);

        this.toPay = new ArrayList<Double>();
        this.purchases = new ArrayList<Purchase>();
    }

    public void addToPay(double amount) {
        this.toPay.add(amount);
    }

    public void addToPurchases(Purchase purchase) {
        this.purchases.add(purchase);
    }

    public String toString() {
        String str = "\n###########################################\n# Nome    - " + this.getName() + "\n";
        str += "# CPF     - " + this.getDocument() + "\n";
        str += "# Saldo   - " + this.getBalance() + "\n";
        str += "# A pagar - R$ " + this.getToPayTotal() + "\n";
        str += "###########################################\n";

        return str;
    }

    public double getToPayTotal() {
        double total = 0;
        for (int i = 0; i < this.toPay.size(); i++) {
            total += this.toPay.get(i);
        }
        return total;
    }

}
