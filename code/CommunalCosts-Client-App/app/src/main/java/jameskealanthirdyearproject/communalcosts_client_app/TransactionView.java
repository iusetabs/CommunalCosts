package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;

public class TransactionView extends AppCompatActivity implements View.OnClickListener {

    private EditText description, origin, value;
    private Button saveChanges, backToTransactions;
    private Intent transactionListView, settingsIntent;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser userRef;
    private DatabaseReference dbRef;
    private String collectiveId;
    private ArrayList<TransactionObj> trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_transaction_view);

        settingsIntent = new Intent(TransactionView.this, SettingsActivity.class);
        saveChanges = (Button) findViewById(R.id.saveBtn);
        backToTransactions = (Button) findViewById(R.id.backBtn);

        description = (EditText) findViewById(R.id.transactionDescription);
        origin = (EditText) findViewById(R.id.transactionPayee);
        value = (EditText) findViewById(R.id.transactionValue);

        description.setText(getIntent().getStringExtra("CURRENT_TRANSACTION_DESCRIPTION"), TextView.BufferType.NORMAL);
        origin.setText(getIntent().getStringExtra("CURRENT_TRANSACTION_PAYEE"), TextView.BufferType.NORMAL);
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        trans = (ArrayList<TransactionObj>) args.getSerializable("ARRAYLIST");
        Integer val = new Integer(0);
        value.setText(String.format("%d", getIntent().getIntExtra("CURRENT_TRANSACTION_VALUE", val)), TextView.BufferType.NORMAL);
        dbRef = FirebaseDatabase.getInstance().getReference();

        backToTransactions.setOnClickListener(this);
        saveChanges.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collectiveId = getIntent().getStringExtra("CURRENT_COLLECTIVE_ID");
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
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings){
            settingsIntent.putExtra("CURRENT_COLLECTIVE_ID", collectiveId);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        TransactionObj transactionObj = new TransactionObj(); //FIXME
        transactionObj.setEditedBy(userRef.getUid());
        transactionObj.setDescription(description.getText().toString().trim()); //add creator first to members
        transactionObj.setPayee(origin.getText().toString().trim());
        transactionObj.setValueOfT(Integer.parseInt(value.getText().toString().trim()));
        final String collectiveId = getIntent().getStringExtra("CURRENT_COLLECTIVE_ID"); // FIXME: 24/02/18 doesnt transfer collective id properly!
        dbRef.child("collectives/" + collectiveId +"/transactions").setValue(transactionObj);
        Toast.makeText(TransactionView.this,"Transaction Edited", Toast.LENGTH_SHORT).show();
    }

}
