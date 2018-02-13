package jameskealanthirdyearproject.communalcosts_client_app;

import android.util.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by kealan on 07/02/18.
 */

public class Collective {
    private ArrayList<Pair<String, Integer>> members = new ArrayList<Pair<String, Integer>>();
    private String databaseFinancials;
    private String collectiveName;
    private String collectiveId;

    public Collective(){}

    public Collective(String name, String id){
        this.collectiveId = id;
        this.collectiveName = name;
    }

    /*public Collective Collective(){
        this.members = new ArrayList<Pair<String, Integer>>();
        return this;
    }*/


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



    public void finances(Transaction transaction){
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
    /*public Collective add(Account user, Integer privilege) {
        if (privilege >= 0 & privilege <=2){
            this.members.add(user.getUid(), privilege);
        }
        else{
            System.out.print("invalid privilege level");
        }
    }*/
    /*we don't know how to add m*/
}
