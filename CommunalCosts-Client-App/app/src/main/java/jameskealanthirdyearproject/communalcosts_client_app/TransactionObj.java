package jameskealanthirdyearproject.communalcosts_client_app;

/**
 * Created by kealan on 11/02/18.
 */

public class TransactionObj {
    public String description;
    public Integer value;
    public String payee;

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public TransactionObj(){}

    public TransactionObj(String descrpt, Integer val, String paying){
        this.description = descrpt;
        this.value = val;
        this.payee = paying;
    }
}
