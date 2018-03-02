package jameskealanthirdyearproject.communalcosts_client_app;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kealan on 11/02/18.
 */

public class TransactionObj {
    public String description;
    public int value;
    public String payee;
    public String creator;

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String id;
    public String editedBy;

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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public TransactionObj(){}

    public TransactionObj(String descrpt, Integer val, String paying){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser userRef = firebaseAuth.getCurrentUser();
        this.description = descrpt;
        this.value = val;
        this.payee = paying;
        this.creator = userRef.getUid().toString();

    }
    public void updateValues(DataSnapshot dataSnapshot) { //this will update the values of the class
        this.setDescription(dataSnapshot.child("transactions").getValue(TransactionObj.class).getDescription());
        this.setValue(dataSnapshot.child("transactions").getValue(TransactionObj.class).getValue());
        this.setPayee(dataSnapshot.child("transactions").getValue(TransactionObj.class).getPayee());
    }
}
