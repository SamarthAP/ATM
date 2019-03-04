package ATM_0354;

import java.math.BigDecimal;

public class ChequingAccount extends AssetAccount {

    private boolean primary;

    private BigDecimal minimumAllowedBalance;

    public ChequingAccount(int id) {
        super(id);
        this.primary = false;
        this.minimumAllowedBalance = new BigDecimal(-100);
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isPrimary() {
        return primary;
    }
}
