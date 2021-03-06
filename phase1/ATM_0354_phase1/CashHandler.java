package ATM_0354_phase1;

import ATM_0354_phase2.CashObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CashHandler{

    private ArrayList<ATM_0354_phase2.CashObject> cash;
    private boolean hasEnoughBills;

    /**
    Precondition: the CashObjects in cash all have different bill values.
     */
    public CashHandler(ArrayList<ATM_0354_phase2.CashObject> cash){
        this.cash = cash;
    }

    public CashHandler(){
        this.cash = new ArrayList<>();
        this.hasEnoughBills = false;
    }

    public void addCash(BigDecimal billValue, int count){
        for(ATM_0354_phase2.CashObject cashObject : cash){
            if (cashObject.getCashValue().equals(billValue)){
                cashObject.setCount(cashObject.getCount() + count);
                updateRestockStatus();
                return;
            }
        }
        ATM_0354_phase2.CashObject cashObject = new ATM_0354_phase2.CashObject(billValue, count);
        this.cash.add(cashObject);
        updateRestockStatus();
    }

    public void withdrawCash(BigDecimal billValue, int count){
        for(ATM_0354_phase2.CashObject cashObject : cash){
            if (cashObject.getCashValue().equals(billValue)){
                if (cashObject.getCount() < count){
                    System.out.println("Not enough bills to withdraw " + count + " bills.");
                    return;
                }
                else{
                    cashObject.setCount(cashObject.getCount() - count);
                    updateRestockStatus();
                    return;
                }
            }
        }
        System.out.println("Not an existing bill denomination: " + billValue);
    }

    private void updateRestockStatus(){
        for(ATM_0354_phase2.CashObject cashObject: cash){
            if (cashObject.needsRestocking()) {
                sendAlert();
                this.hasEnoughBills = false;
                return;
            }
        }

        this.hasEnoughBills = true;
    }

    public boolean hasEnoughBills(){
        return hasEnoughBills;
    }

    public void sendAlert(){
        try {
            File file = new File("phase1/ATM_0354_phase1/ATM_0354_phase2.Files/alerts.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for(ATM_0354_phase2.CashObject c : cash) {
                writer.write(c.getCashValue() + ", " + c.getCount());
                writer.newLine();
            }

            writer.close();
        }
        catch (Exception e) {
            System.out.println("ERROR: Could not create file.");
        }
    }

    public ArrayList<CashObject> getCash(){
        return cash;
    }

}
