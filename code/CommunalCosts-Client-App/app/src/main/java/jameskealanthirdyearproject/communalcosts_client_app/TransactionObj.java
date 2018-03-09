package jameskealanthirdyearproject.communalcosts_client_app;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by kealan on 11/02/18.
 */

public class TransactionObj {
    private String title;
    private String description;
    private int valueOfT;
    private String payee;
    private String creator;
    private String id;
    private String editedBy;
    private ArrayList<String> youOweMe = new ArrayList<>();
    private ArrayList<String> youPaidMe = new ArrayList<>();

    public TransactionObj(){}

    public TransactionObj(String descrpt, Integer val, String paying, String creator){
        this.description = descrpt;
        this.valueOfT = val;
        this.payee = paying;
        this.creator = creator;

    }

    public TransactionObj(String descrpt, Integer val, String paying){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser userRef = firebaseAuth.getCurrentUser();
        this.description = descrpt;
        this.valueOfT = val;
        this.payee = paying;
        this.creator = userRef.getUid();

    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public void setValueOfT(int value) {
        this.valueOfT = value;
    }

    public String getCreator() {
        return creator;
    }

    public ArrayList<String> getYouOweMe() {
        return youOweMe;
    }

    public void setYouOweMe(ArrayList<String> youOweMe) {
        this.youOweMe = youOweMe;
    }
    public void addToYouOweMe(String s){
        this.youOweMe.add(s);
    }
    public void removeAllOweMe(){
        this.youOweMe.clear();
    }

    public ArrayList<String> getYouPaidMe() {
        return youPaidMe;
    }

    public void setYouPaidMe(ArrayList<String> youPaidMe) {
        this.youPaidMe = youPaidMe;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getValueOfT() {
        return valueOfT;
    }


    public void updateValues(DataSnapshot dataSnapshot) { //this will update the values of the class
        if(dataSnapshot.exists()) {
            this.setDescription(dataSnapshot.child("transactions").getValue(TransactionObj.class).getDescription());
            this.setValueOfT(dataSnapshot.child("transactions").getValue(TransactionObj.class).getValueOfT());
            this.setPayee(dataSnapshot.child("transactions").getValue(TransactionObj.class).getPayee());
        }
    }
}
