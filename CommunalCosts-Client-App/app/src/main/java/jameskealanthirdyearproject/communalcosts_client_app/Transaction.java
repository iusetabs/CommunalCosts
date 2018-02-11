package jameskealanthirdyearproject.communalcosts_client_app;

/**
 * Created by kealan on 11/02/18.
 */

public class Transaction {
    public String description;
    public Integer value;

    public Transaction(){}

    public Transaction(String descrpt, Integer val){
        this.description = descrpt;
        this.value = val;
    }
}
