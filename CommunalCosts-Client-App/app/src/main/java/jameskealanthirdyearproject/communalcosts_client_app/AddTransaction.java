package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.sql.Timestamp;

import static android.content.ContentValues.TAG;

public class AddTransaction extends AppCompatActivity implements View.OnClickListener {

    private EditText description, payee, val;
    private DatabaseReference dbRef;
    private TransactionObj myTransaction;
    private FirebaseAuth firebaseAuth;
    private Button createTransaction;
    private Intent transactionView;
    private DataSnapshot collectiveSnapshot;
    private String collectiveId;
    private FirebaseUser userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        dbRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userRef = firebaseAuth.getCurrentUser();

        description = (EditText) findViewById(R.id.transactionDetails);
        payee = (EditText) findViewById(R.id.transactionSource);
        val = (EditText) findViewById(R.id.value);

        createTransaction = (Button) findViewById(R.id.CreateTransaction);
        createTransaction.setOnClickListener(this);
        transactionView = new Intent(AddTransaction.this, CollectiveViewActivity.class);
        myTransaction = new TransactionObj();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectiveSnapshot = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Failed to read value.", databaseError.toException());
            }
        });
        dbRef.child("users").child(userRef.getUid()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                payee.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v){
        if(v == createTransaction){
            createTransaction(collectiveSnapshot);
            finish();
            transactionView.putExtra("CURRENT_COLLECTIVE_ID", collectiveId);
            startActivity(transactionView);
        }

    }

    private void createTransaction(DataSnapshot dataSnapshot) {//add datasnapshot here to grab the collective object from add the transaction to and update it
        collectiveId = getIntent().getStringExtra("CURRENT_COLLECTIVE_ID");
        myTransaction.setDescription(description.getText().toString().trim());
        myTransaction.setCreator(firebaseAuth.getUid());

        myTransaction.setPayee(payee.getText().toString().trim());
        try {
            myTransaction.setValue(Integer.parseInt(val.getText().toString().trim()));
        }
        catch (NumberFormatException e){
            Toast.makeText(AddTransaction.this,"Incorrect value parameter", Toast.LENGTH_SHORT).show();
            return;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        myTransaction.setId(timestamp + userRef.getUid());

        CollectiveObj collectiveObj = dataSnapshot.child("collectives/" + collectiveId).getValue(CollectiveObj.class);
        try {
            collectiveObj.addTransaction(myTransaction);
        }
        catch (NullPointerException e){}
        dbRef.child("collectives/" + collectiveId).setValue(collectiveObj);
        Toast.makeText(AddTransaction.this,"Transaction Created", Toast.LENGTH_SHORT).show();
    }
}
