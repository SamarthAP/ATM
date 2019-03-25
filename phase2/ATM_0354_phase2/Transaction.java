package ATM_0354_phase2;

import com.sun.istack.internal.Nullable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Transaction {

    private LocalDateTime date;
    private Account accountFrom, accountTo;
    private BigDecimal value;

    /**
     * accountFrom can be null iff instanceof Deposit
     * accountTo can be null iff instanceof Bill or Withdrawal
     */
    public Transaction(@Nullable Account accountFrom, @Nullable Account accountTo, BigDecimal value) {
        this.date = LocalDateTime.now();
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.value = value;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public BigDecimal getValue() {
        return value;
    }

    public abstract void process();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " : Account ID Number " + this.accountFrom + " sent $" + value +
                " to " + this.accountTo;
    }
}
