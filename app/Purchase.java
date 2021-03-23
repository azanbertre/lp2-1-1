package app;

import java.util.ArrayList;

import app.payments.PaymentMethod;


public class Purchase {

    private String buyerDocument;
    private String sellerDocument;
    private PaymentMethod paymentMethod;

    private ArrayList<Product> products;

    public Purchase(String buyerDocument, String sellerDocument, PaymentMethod paymentMethod) {
        this.buyerDocument = buyerDocument;
        this.sellerDocument = sellerDocument;
        this.paymentMethod = paymentMethod;

        this.products = new ArrayList<Product>();
    }

    public String getBuyerDocument() {
        return this.buyerDocument;
    }

    public String getSellerDocument() {
        return this.sellerDocument;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public String toString() {
        String str = " || Compra em " + this.paymentMethod.getName() + " de valor R$ " + this.getValue() + ", vendido por CNPJ " + this.sellerDocument + " e comprado por CPF " + this.buyerDocument;

        return str;
    }

    public double getValue() {
        double total = 0;
        for (int i = 0; i < this.products.size(); i++) {
            total += this.products.get(i).getPrice();
        }
        return total;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

}
