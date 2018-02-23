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
/*We need to clean up this file - it's kind of messy! */
    private ArrayList<TransactionObj> transactions = new ArrayList<>();

    /*Member Structure*/
    private ArrayList<ArrayList<String>> allUsers= new ArrayList<>();
    private ArrayList<String> newMembers = new ArrayList<>();
    private ArrayList<String> ordinaryMembers = new ArrayList<>();
    private ArrayList<String> contributorMembers = new ArrayList<>();
    private ArrayList<String> administratorMembers = new ArrayList<>();
    private String collectiveName;
    private String collectiveId;
    private String collectiveType;
    private String creator;


    public CollectiveObj(){
        this.collectiveId = "";
        this.collectiveType = "";
        this.collectiveId = "";
        this.creator = "";
    }
    private void initaliseMemberStructure() {
        if(this.allUsers.size() == 0){
            this.allUsers.add(newMembers);
            this.allUsers.add(ordinaryMembers);
            this.allUsers.add(contributorMembers);
            this.allUsers.add(administratorMembers);
        }

    }
    public CollectiveObj(String name, String type, String id, String c, ArrayList<String> mems){
        this.collectiveId = id;
        this.collectiveType = type;
        this.collectiveName = name;
        this.creator = c;
        this.newMembers = mems;
    }

    /*MemberFunctions*/
    public void addNewMembers(String s) {
        if(!this.newMembers.contains(s))
            this.newMembers.add(s);
    }
    public ArrayList<TransactionObj> getTransactions() {
        return transactions;
    }



    public void addTransaction(TransactionObj transaction) {
        this.transactions.add(transaction);
    }

    public void removeTransaction(TransactionObj transactionObj){
        this.transactions.remove(transactions);
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArrayList<String> getnewMembers() {
        return newMembers;
    }


    public String getCollectiveName() {
        return collectiveName;

    }

    public void setMembers(ArrayList<String> members) {
        this.newMembers = members;
    }

    public String getCollectiveType() {
        return collectiveType;
    }

    public void setCollectiveType(String collectiveType) {
        this.collectiveType = collectiveType;
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
}
