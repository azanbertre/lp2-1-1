package app.accounts;

import java.util.ArrayList;

import app.Product;
import app.Purchase;


public class Seller extends Account {

    private ArrayList<Double> toReceive;
    private ArrayList<Purchase> sales;
    private ArrayList<Product> products;

    public Seller(String name, double balance, String document) {
        super(name, balance, document);

        this.toReceive = new ArrayList<Double>();
        this.sales = new ArrayList<Purchase>();
        this.products = new ArrayList<Product>();
    }

    public void addToReceive(double amount) {
        this.toReceive.add(amount);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addToSales(Purchase sale) {
        this.sales.add(sale);
    }

    public Product getProduct(String code) {
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getCode().equals(code)) {
                return this.products.get(i);
            }
        }

        return null;
    }

    public String toString() {
        String str = "\n###########################################\n# Nome      - " + this.getName() + "\n";
        str += "# CNPJ      - " + this.getDocument() + "\n";
        str += "# Saldo     - " + this.getBalance() + "\n";
        str += "# A receber - R$ " + this.getToReceiveTotal() + "\n";
        str += "## Produtos:\n";

        for (int i = 0; i < this.products.size(); i++) {
            str += "## " + this.products.get(i).getCode() + " - " + this.products.get(i).getName() + " | R$ " + this.products.get(i).getPrice();
        }

        str += "\n###########################################\n";

        return str;
    }

    public double getToReceiveTotal() {
        double total = 0;
        for (int i = 0; i < this.toReceive.size(); i++) {
            total += this.toReceive.get(i);
        }
        return total;
    }

}
