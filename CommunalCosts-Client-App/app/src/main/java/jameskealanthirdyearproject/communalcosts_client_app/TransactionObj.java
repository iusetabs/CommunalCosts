package jameskealanthirdyearproject.communalcosts_client_app;

/**
 * Created by kealan on 11/02/18.
 */

public class TransactionObj {
    public String description;

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

    public Integer value;

    public TransactionObj(){}

    public TransactionObj(String descrpt, Integer val){
        this.description = descrpt;
        this.value = val;
    }
}
