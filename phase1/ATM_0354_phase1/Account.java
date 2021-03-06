package ATM_0354_phase1;

import ATM_0354_phase2.Bill;
import ATM_0354_phase2.Deposit;
import ATM_0354_phase2.MoneyTransferException;
import ATM_0354_phase2.Transaction;
import ATM_0354_phase2.Transfer;
import ATM_0354_phase2.Withdrawal;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Account {
    private ArrayList<ATM_0354_phase2.Transaction> transactions;
    private int id;
    private BigDecimal balance;
    private LocalDateTime dateOfCreation;
    private boolean transferIn, transferOut;

    // TODO: ask if all accounts have a minimum balance; currently only ChequingAccount does

    public Account(int id) {
        this.transactions = new ArrayList<>();
        this.balance = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.id = id;
        this.dateOfCreation = LocalDateTime.now();
        this.transferIn = true;
        this.transferOut = true;
    }
    public Account(int id, BigDecimal balance, LocalDateTime dateOfCreation, ArrayList<ATM_0354_phase2.Transaction> transactions){
        this.transactions = transactions;
        this.balance = balance;
        this.id = id;
        this.dateOfCreation = dateOfCreation;
        this.transferIn = true;
        this.transferOut = true;
    }

    public void transferMoneyIn(BigDecimal value) throws ATM_0354_phase2.MoneyTransferException {
        if (this.canTransferIn()) {
            this.balance = this.balance.add(value.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else throw new ATM_0354_phase2.MoneyTransferException("Can't transfer money into this account");

    }

    public void transferMoneyOut(BigDecimal value) throws ATM_0354_phase2.MoneyTransferException {
        // Note: Balance can be negative after using this method
        if (canTransferOut()) {
            this.balance = this.balance.subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else throw new ATM_0354_phase2.MoneyTransferException("Can't transfer money out of this account");
    }

    public void sendTransfer(ATM_0354_phase2.Transfer transfer) throws ATM_0354_phase2.MoneyTransferException {
        transferMoneyOut(transfer.getValue());
        addTransaction(transfer);
    }

    public void receiveTransfer(Transfer transfer) throws ATM_0354_phase2.MoneyTransferException {
        transferMoneyIn(transfer.getValue());
        addTransaction(transfer);
    }

    public void forceTransferIn(BigDecimal value) {
        this.balance = this.balance.add(value.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void forceTransferOut(BigDecimal value) {
        this.balance = this.balance.subtract(value.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void addTransaction(ATM_0354_phase2.Transaction transaction) {
        this.transactions.add(transaction);
    }

    public ATM_0354_phase2.Transaction getLastTransaction() throws IndexOutOfBoundsException{
        try {
            return this.transactions.get(this.transactions.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Couldn't get last transaction!");
            return null;
        }
    }

    public void payBill(BigDecimal amount) throws ATM_0354_phase2.MoneyTransferException {
        this.transferMoneyOut(amount);
        ATM_0354_phase2.Transaction newTransaction = new ATM_0354_phase2.Transaction(this.id, -99, amount, true);
        this.transactions.add(newTransaction);
        try(FileWriter fw = new FileWriter("phase1/ATM_0354_phase1/ATM_0354_phase2.Files/outgoing.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw))
        {
            pw.println(newTransaction.toString());
        } catch (IOException e) {
            System.out.println("IOException writing to outgoing.txt");
        }
    }

    public void processDeposit(Deposit deposit) throws ATM_0354_phase2.MoneyTransferException {
        this.transferMoneyIn(deposit.getValue());
        addTransaction(deposit);
    }

    public void processWithdrawal(Withdrawal withdrawal) throws ATM_0354_phase2.MoneyTransferException {
        this.transferMoneyOut(withdrawal.getValue());
        addTransaction(withdrawal);
    }

    public void processCheque(Cheque cheque) throws ATM_0354_phase2.MoneyTransferException {
        this.transferMoneyIn(cheque.getValue());
        addTransaction(cheque);
    }

    public void processBill(Bill bill) throws MoneyTransferException {
        this.transferMoneyOut(bill.getValue());
        addTransaction(bill);
    }

    public boolean doesTransactionExist(ATM_0354_phase2.Transaction transaction) {
        return transactions.contains(transaction);
    }

    public void deleteSpecificTransaction(ATM_0354_phase2.Transaction transaction) {
        // Should only be used SOME times
        this.transactions.remove(transaction);
    }

    public LocalDateTime getDateOfCreation() {
        return this.dateOfCreation;
    }

    public BigDecimal getBalance() {
        return this.balance.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public int getId() {
        return this.id;
    }

    public ATM_0354_phase2.Transaction undoTransaction() {
        return this.transactions.remove(this.transactions.size() - 1);
    }

    public boolean canTransferIn() {
        return transferIn;
    }

    public boolean canTransferOut() {
        return transferOut;
    }

    public void setTransferIn(boolean cantransferIn) {
        this.transferIn = cantransferIn;
    }

    public void setTransferOut(boolean cantransferOut) {
        this.transferOut = cantransferOut;
    }

    public void setBalance(BigDecimal newBalance) {
        this.balance = newBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account ID: " + this.id + "\nAccount Balance: " + this.balance;
    }

    public void writeTransactions(String username){
        try{
            String filepath = "phase1/ATM_0354_phase1/ATM_0354_phase2.Files/transactions.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filepath), true));
            //TODO: Format???
            for (Transaction transaction : transactions){
                writer.write(username + "," + transaction.getAccountFrom()
                        + "," + transaction + "," + transaction.getAccountTo()
                        + "," + transaction.getValue() + "," + transaction.getDate());
                writer.newLine();
            }
            writer.close();
        } catch(IOException e){
            System.out.println(e.toString());
            System.out.println("IOException when writing transaction to transactions.txt");
        }
    }
}
