package ATM_0354_phase2;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Account {
    private ArrayList<Transaction> transactions;
    private int id;
    private BigDecimal balance;
    private LocalDateTime dateOfCreation;
    private boolean transferIn, transferOut;
    private String username;

    public Account(String username, int id) {
        this.username = username;
        this.transactions = new ArrayList<>();
        this.balance = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.id = id;
        this.dateOfCreation = Main.atm.getDateTime();
        this.transferIn = true;
        this.transferOut = true;
    }
    public Account(String username, int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<Transaction> transactions){
        this.username = username;
        this.transactions = transactions;
        this.balance = balance;
        this.id = id;
        this.dateOfCreation = dateOfCreation;
        this.transferIn = true;
        this.transferOut = true;
    }

    public void transferMoneyIn(BigDecimal value) throws MoneyTransferException {
        if (this.canTransferIn()) {
            this.balance = this.balance.add(value.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else throw new MoneyTransferException("Can't transfer money into this account");

    }

    public void transferMoneyOut(BigDecimal value) throws MoneyTransferException {
        // Note: Balance can be negative after using this method
        if (canTransferOut()) {
            this.balance = this.balance.subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else throw new MoneyTransferException("Can't transfer money out of this account");
    }

    public void forceTransferIn(BigDecimal value) {
        this.balance = this.balance.add(value.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void forceTransferOut(BigDecimal value) {
        this.balance = this.balance.subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions(){return this.transactions;}
    public LocalDateTime getDateOfCreation() {
        return this.dateOfCreation;
    }

    public BigDecimal getBalance() {
        return this.balance.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public int getId() {
        return this.id;
    }

    public void undoTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
    }

    public boolean canTransferIn() {
        return transferIn;
    }

    public boolean canTransferOut() {
        return transferOut;
    }

    public void setTransferOut(boolean canTransferOut) {
        this.transferOut = canTransferOut;
    }

    public void setBalance(BigDecimal newBalance) {
        this.balance = newBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = dateOfCreation.format(formatter);
        return "\tAccount ID: " + this.id +
                "\n\tAccount Balance: " + this.balance +
                "\n\tDate of Creation: " + formatDateTime + "\n";
    }

    public String getSummary(int id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = dateOfCreation.format(formatter);
        return "\tAccount ID: " + id +
                "\n\tAccount Balance: " + this.balance +
                "\n\tDate of Creation: " + formatDateTime + "\n";
    }


    public void writeTransactions(){
        try{
            String filepath = "phase2/ATM_0354_phase2/Files/transactions.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filepath), true));
            for (Transaction transaction : transactions){
                writer.write(username + "," + transaction.serialize());
                writer.newLine();
            }
            writer.close();
        } catch(IOException e){
            System.out.println(e.toString());
            System.out.println("IOException when writing transaction to transactions.txt");
        }
    }
}
