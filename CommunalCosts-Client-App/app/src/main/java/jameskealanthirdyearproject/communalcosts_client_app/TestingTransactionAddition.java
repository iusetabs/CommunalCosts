package jameskealanthirdyearproject.communalcosts_client_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestingTransactionAddition extends AppCompatActivity {

    private EditText test;
    private TransactionObj myTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_transaction_addition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myTransaction = new TransactionObj();
        test = (EditText) findViewById(R.id.testDescription);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayAccountDetails(dataSnapshot);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void displayAccountDetails(DataSnapshot dataSnapshot){
        myTransaction.setValue(dataSnapshot.child("transactions").getValue(TransactionObj.class).getValue());
        test.setText(String.format("%d",myTransaction.getValue()), TextView.BufferType.EDITABLE);

    }

}
