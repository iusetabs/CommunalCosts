package jameskealanthirdyearproject.communalcosts_client_app;

import android.util.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static android.content.ContentValues.TAG;

/**
 * Created by kealan on 07/02/18.
 */

public class CollectiveObj {

    private ArrayList<TransactionObj> transactions = new ArrayList<>();
    private ArrayList<String> members= new ArrayList<>();
    private String databaseFinancials;
    private String collectiveName;
    private String collectiveId;
    private String creator;

    public CollectiveObj(){}

    public ArrayList<TransactionObj> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<TransactionObj> transactions) {
        this.transactions = transactions;
    }

    public CollectiveObj(String name, String id, String c, ArrayList<String> mems){
        this.collectiveId = id;
        this.collectiveName = name;
        this.creator = c;
        this.members = mems;
        this.transactions = new ArrayList<>();

    }

   /* public ArrayList<Pair<String, Integer>> getMembers() {
        return members;
    }*/

    public String getDatabaseFinancials() {
        return databaseFinancials;
    }

    public void setDatabaseFinancials(String databaseFinancials) {
        this.databaseFinancials = databaseFinancials;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    /*public void setMembers(ArrayList<Pair<String, Integer>> members) {
        this.members = members;
    }*/
/*public collectiveObj collectiveObj(){
        this.members = new ArrayList<Pair<String, Integer>>();
        return this;
    }*/


    public ArrayList<String> getMembers() {
        return members;
    }

    public void addMembers(String s) {
        this.members.add(s); //fixme 14th feb 11:43
    }

    public String getCollectiveName() {
        return collectiveName;

    }
    public void setCollectiveName(String collectiveName) {
        this.collectiveName = collectiveName;
    }

    public String getCollectiveId() {
        return collectiveId;
    }

    public void setCollectiveId(String collectiveId) {
        this.collectiveId = collectiveId;
    }

    public void finances(TransactionObj transaction){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("transaction");
        myRef.setValue(transaction);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Failed to read value.", databaseError.toException());

            }
        });
    }
    /*public collectiveObj add(accountObj user, Integer privilege) {
        if (privilege >= 0 & privilege <=2){
            this.members.add(user.getUid(), privilege);
        }
        else{
            System.out.print("invalid privilege level");
        }
    }*/
    /*we don't know how to add m*/
}
