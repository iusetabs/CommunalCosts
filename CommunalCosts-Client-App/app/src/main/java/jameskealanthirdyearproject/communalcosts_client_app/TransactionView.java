package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TransactionView extends AppCompatActivity implements View.OnClickListener {

    private EditText description, origin, value;
    private Button saveChanges, backToTransactions;
    private Intent transactionListView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser userRef;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_view);

        saveChanges = (Button) findViewById(R.id.saveBtn);
        backToTransactions = (Button) findViewById(R.id.backBtn);

        description = (EditText) findViewById(R.id.transactionDescription);
        origin = (EditText) findViewById(R.id.transactionPayee);
        value = (EditText) findViewById(R.id.transactionValue);

        description.setText(getIntent().getStringExtra("CURRENT_TRANSACTION_DESCRIPTION"), TextView.BufferType.NORMAL);
        origin.setText(getIntent().getStringExtra("CURRENT_TRANSACTION_PAYEE"), TextView.BufferType.NORMAL);
        Integer val = new Integer(0);
        value.setText(String.format("%d", getIntent().getIntExtra("CURRENT_TRANSACTION_VALUE", val)), TextView.BufferType.NORMAL);
        dbRef = FirebaseDatabase.getInstance().getReference();

        backToTransactions.setOnClickListener(this);
        saveChanges.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String collectiveId = getIntent().getStringExtra("CURRENT_COLLECTIVE_ID");
        System.out.println(collectiveId);

        transactionListView = new Intent(TransactionView.this, CollectiveViewActivity.class);
        transactionListView.putExtra("CURRENT_COLLECTIVE_ID", collectiveId);

        FloatingActionButton transactionConfirmationBtn = (FloatingActionButton) findViewById(R.id.confirmTransaction);
        transactionConfirmationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v){
        if(v == saveChanges){
            saveTransaction();
            finish();
            startActivity(transactionListView);
        }
        else if (v == backToTransactions){
            finish();
            startActivity(transactionListView);
        }
    }

    private void saveTransaction() {
        firebaseAuth = FirebaseAuth.getInstance();
        userRef = firebaseAuth.getCurrentUser();
        TransactionObj transactionObj = new TransactionObj();
        transactionObj.setEditedBy(userRef.getUid());
        transactionObj.setDescription(description.getText().toString().trim()); //add creator first to members
        transactionObj.setPayee(origin.getText().toString().trim());
        transactionObj.setValue(Integer.parseInt(value.getText().toString().trim()));
        final String collectiveId = getIntent().getStringExtra("CURRENT_COLLECTIVE_ID"); // FIXME: 24/02/18 doesnt transfer collective id properly!
        dbRef.child("collectives/" + collectiveId +"/transactions").setValue(transactionObj);
        Toast.makeText(TransactionView.this,"Transaction Edited", Toast.LENGTH_SHORT).show();
    }

}
