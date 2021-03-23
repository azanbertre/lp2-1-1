package app.accounts;


public class Account {

    private String name;
    private double balance;
    private String document;

    public Account(String name, double balance, String document) {
        this.name = name;
        this.balance = balance;
        this.document = document;
    }

    public boolean withdraw(double amount) {
        if (this.balance < amount) {
            return false;
        }

        this.balance -= amount;

        return true;
    }

    public boolean deposit(double amount) {
        this.balance += amount;

        return true;
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getDocument() {
        return this.document;
    }

}
