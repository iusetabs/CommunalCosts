package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class AddTransaction extends AppCompatActivity implements View.OnClickListener {

    private EditText description, payee, val;
    private DatabaseReference dbRef;
    private TransactionObj myTransaction;
    private FirebaseAuth firebaseAuth;
    private Button createTransaction;
    private Intent transactionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        description = (EditText) findViewById(R.id.transactionDetails);
        payee = (EditText) findViewById(R.id.transactionSource);
        val = (EditText) findViewById(R.id.value);
        dbRef = FirebaseDatabase.getInstance().getReference();
        createTransaction = (Button) findViewById(R.id.CreateTransaction);
        createTransaction.setOnClickListener(this);
        transactionView = new Intent(AddTransaction.this, CollectiveViewActivity.class);
        myTransaction = new TransactionObj();
        /*dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayAccountDetails(dataSnapshot);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });*/
    }

    @Override
    public void onClick(View v){
        if(v == createTransaction){
            createTransaction();
            finish();
            startActivity(transactionView);
        }

    }
    /*private void displayAccountDetails(DataSnapshot dataSnapshot){
        description.getText();
        myTransaction.setDescription(dataSnapshot.child(user.getUid()).getValue(AccountObj.class).getEmail());


        myAccount.setName(dataSnapshot.child(user.getUid()).getValue(AccountObj.class).getName());
        userName.setText(myAccount.getName(), TextView.BufferType.EDITABLE);

    }*/

    private void createTransaction() {
        /*FirebaseUser userRef = firebaseAuth.getCurrentUser();*/
        myTransaction.setDescription(description.getText().toString().trim());

        myTransaction.setPayee(payee.getText().toString().trim());
        try {
            myTransaction.setValue(Integer.parseInt(val.getText().toString().trim()));
        }
        catch (NumberFormatException e){
            Toast.makeText(AddTransaction.this,"Incorrect value parameter", Toast.LENGTH_SHORT).show();
            return;
        }
        dbRef.child("transactions").setValue(myTransaction);
        //dbRef.child("collectives").child(mCollective.getCollectiveName()).child("Members").setValue(mCollective.getMembers());
        Toast.makeText(AddTransaction.this,"Transaction Created", Toast.LENGTH_SHORT).show();
    }
}
