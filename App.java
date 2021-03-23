import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import app.accounts.Buyer;
import app.accounts.Seller;

import app.payments.PaymentMethod;
import app.payments.Credit;
import app.payments.Debit;
import app.payments.Pix;
import app.payments.Ticket;

import app.Product;
import app.Purchase;


public class App {

    private ArrayList<Buyer> buyers;
    private ArrayList<Seller> sellers;
    private ArrayList<Product> products;
    private ArrayList<Purchase> purchases;

    private Scanner myScanner;
    private String currentCommand;

    public static void main(String[] args) {

        App myApp = new App();
        myApp.startApp();

    }

    public App() {
        this.buyers = new ArrayList<Buyer>();
        this.sellers = new ArrayList<Seller>();
        this.products = new ArrayList<Product>();
        this.purchases = new ArrayList<Purchase>();

        this.products.add(new Product("1", "produto", 25));
        this.buyers.add(new Buyer("comprador", 500, "1"));
        this.sellers.add(new Seller("vendedor", 500, "1"));
    }

    public void startApp() {

        this.myScanner = new Scanner(System.in);

        this.displayMenu();

        this.currentCommand = this.myScanner.next();

        while (true) {
            if (this.handleCommand(this.currentCommand)) {
                break;
            };
            this.displayMenu();
            this.currentCommand = this.myScanner.next();
        }

    }

    private void displayMenu() {
        System.out.println("#####################################");
        System.out.println("0 - Cadastrar COMPRA");
        System.out.println("1 - Cadastrar comprador");
        System.out.println("2 - Cadastrar vendedor");
        System.out.println("3 - Cadastrar produto");
        System.out.println("4 - Adicionar produto ao vendedor");
        System.out.println("5 - Informações de comprador");
        System.out.println("6 - Informações de vendedor");
        System.out.println("7 - Listar todas as compras\n");
        System.out.println("x - Sair");
        System.out.println("#####################################\n");
    }

    private boolean handleCommand(String command) {

        switch(command) {
            case "0":
                this.makePurchase();
                break;
            case "1":
                this.makeBuyer();
                break;
            case "2":
                this.makeSeller();
                break;
            case "3":
                this.makeProduct();
                break;
            case "4":
                this.addProductToSeller();
                break;
            case "5":
                this.buyerInfo();
                break;
            case "6":
                this.sellerInfo();
                break;
            case "7":
                this.listPurchases();
                break;
            default:
                return true;
        }

        return false;

    }

    private void makePurchase() {

        if (this.buyers.size() <= 0) {
            System.out.println("Nenhum comprador cadastrado ainda");
            return;
        }
        if (this.sellers.size() <= 0) {
            System.out.println("Nenhum vendedor cadastrado ainda");
            return;
        }
        if (this.products.size() <= 0) {
            System.out.println("Nenhum produto cadastrado ainda");
            return;
        }

        System.out.print("CPF do comprador: ");
        String cpf = this.myScanner.next();

        Buyer myBuyer = this.getBuyer(cpf);

        if (myBuyer == null) {
            System.out.println("Comprador não existe!!!");
            return;
        }

        System.out.print("CNPJ do vendedor: ");
        String cnpj = this.myScanner.next();

        Seller mySeller = this.getSeller(cnpj);

        if (mySeller == null) {
            System.out.println("Vendedor não existe!!!");
            return;
        }

        String code = "";

        ArrayList<Product> products = new ArrayList<Product>();

        while (true) {
            System.out.println("Código do produto [Digite 'cancelar' para parar de adicionar produtos]");
            code = this.myScanner.next();

            if (code.equals("cancelar")) break;

            Product product = mySeller.getProduct(code);

            if (product == null) {
                System.out.println("Vendedor não possui produto, tente novamente [Digite 'cancelar' para parar de adicionar produtos]");
                continue;
            }

            products.add(product);
        }

        if (products.size() <= 0) {
            System.out.println("Compra cancelada. Motivo: 0 produtos escolhidos");
            return;
        }

        System.out.println(products.size() + " produtos escolhidos");

        System.out.println("Escolher metodo de pagamento: \n1 - PIX\n2 - Boleto\n3 - Débito\n4 - Crédito");
        String method = this.myScanner.next();

        PaymentMethod paymentMethod;

        switch (method) {
            case "1":
                paymentMethod = new Pix();
                break;
            case "2":
                System.out.print("Valor de emissão do boleto: ");
                String value = this.myScanner.next();
                paymentMethod = new Ticket(LocalDate.now().plusDays(3), Double.parseDouble(value.replaceAll(",", ".")));
                break;
            case "3":
                System.out.print("Taxa da operadora do cartão: ");
                String rate = this.myScanner.next();
                paymentMethod = new Debit(Double.parseDouble(rate.replaceAll(",", ".")));
                break;
            case "4":
                System.out.print("Taxa da operadora do cartão: ");
                String rate2 = this.myScanner.next();
                paymentMethod = new Credit(Double.parseDouble(rate2.replaceAll(",", ".")));
                break;
            default:
                System.out.println("Método inválido");
                return;
        }

        Purchase purchase = new Purchase(myBuyer.getDocument(), mySeller.getDocument(), paymentMethod);
        purchase.setProducts(products);

        this.handlePurchase(purchase);

    }

    private void handlePurchase(Purchase purchase) {
        Buyer myBuyer = this.getBuyer(purchase.getBuyerDocument());
        Seller mySeller = this.getSeller(purchase.getSellerDocument());

        PaymentMethod method = purchase.getPaymentMethod();

        if (!method.pay(myBuyer, mySeller, purchase.getValue())) {
            System.out.println("Não foi possivel realizar o pagamento!!!");
            return;
        }

        this.purchases.add(purchase);
        myBuyer.addToPurchases(purchase);
        mySeller.addToSales(purchase);
    }

    private void makeBuyer() {
        System.out.print("Nome do comprador: ");
        String name = this.myScanner.next();

        System.out.print("CPF do comprador: ");
        String cpf = this.myScanner.next();

        System.out.print("Saldo do comprador: ");
        String balance = this.myScanner.next();

        Buyer buyer = new Buyer(name, Double.parseDouble(balance.replaceAll(",", ".")), cpf);

        this.buyers.add(buyer);
    }

    private void makeSeller() {
        System.out.print("Nome do vendedor: ");
        String name = this.myScanner.next();

        System.out.print("CNPJ do vendedor: ");
        String cnpj = this.myScanner.next();

        System.out.print("Saldo do vendedor: ");
        String balance = this.myScanner.next();

        Seller seller = new Seller(name, Double.parseDouble(balance.replaceAll(",", ".")), cnpj);

        this.sellers.add(seller);
    }

    private void makeProduct() {
        System.out.print("Nome do produto: ");
        String name = this.myScanner.next();

        System.out.print("Código do produto: ");
        String code = this.myScanner.next();

        System.out.print("Valor do produto: ");
        String value = this.myScanner.next();

        Product product = new Product(code, name, Double.parseDouble(value.replaceAll(",", ".")));

        this.products.add(product);
    }

    private void addProductToSeller() {
        System.out.print("CNPJ do vendedor: ");
        String cnpj = this.myScanner.next();

        Seller mySeller = this.getSeller(cnpj);

        if (mySeller == null) {
            System.out.println("Vendedor não existe!!!");
            return;
        }

        System.out.print("Código do produto: ");
        String code = this.myScanner.next();

        Product myProduct = this.getProduct(code);

        if (myProduct == null) {
            System.out.println("Produto não existe!!!");
            return;
        }

        if (mySeller.getProduct(code) != null) {
            System.out.println("Vendedor já possui esse produto");
            return;
        }

        mySeller.addProduct(myProduct);
    }

    private void buyerInfo() {
        System.out.print("CPF do comprador: ");
        String cpf = this.myScanner.next();

        Buyer myBuyer = this.getBuyer(cpf);

        if (myBuyer == null) {
            System.out.println("Comprador não existe!!!");
            return;
        }

        System.out.println(myBuyer.toString());
    }

    private void sellerInfo() {
        System.out.print("CNPJ do vendedor: ");
        String cnpj = this.myScanner.next();

        Seller mySeller = this.getSeller(cnpj);

        if (mySeller == null) {
            System.out.println("Vendedor não existe!!!");
            return;
        }

        System.out.println(mySeller.toString());
    }

    private void listPurchases() {
        System.out.println("\n##########################");
        for (int i = 0; i < this.purchases.size(); i++) {
            System.out.println(this.purchases.get(i).toString());
        }
        System.out.println("##########################\n");
    }

    private Seller getSeller(String document) {
        for (int i = 0; i < this.sellers.size(); i++) {
            if (this.sellers.get(i).getDocument().equals(document)) {
                return this.sellers.get(i);
            }
        }

        return null;
    }

    private Buyer getBuyer(String document) {
        for (int i = 0; i < this.buyers.size(); i++) {
            if (this.buyers.get(i).getDocument().equals(document)) {
                return this.buyers.get(i);
            }
        }

        return null;
    }

    private Product getProduct(String code) {
        for (int i = 0; i < this.products.size(); i++) {
            if (this.products.get(i).getCode().equals(code)) {
                return this.products.get(i);
            }
        }

        return null;
    }

}