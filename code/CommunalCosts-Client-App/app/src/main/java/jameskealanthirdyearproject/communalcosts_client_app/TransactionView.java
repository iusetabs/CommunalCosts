package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TransactionView extends AppCompatActivity implements View.OnClickListener {

    private EditText description, title, value, createdBy;
    private Button saveChanges, backToTransactions;
    private Intent transactionListView, settingsIntent;
    private DatabaseReference dbRef;
    private String collectiveId;
    private TransactionObj selectedTrans;
    private FloatingActionButton transactionConfirmationBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_transaction_view);

        saveChanges = (Button) findViewById(R.id.saveBtn);
        backToTransactions = (Button) findViewById(R.id.backBtn);
        description = (EditText) findViewById(R.id.transactionDescription);
        title = (EditText) findViewById(R.id.transactionTitle);
        value = (EditText) findViewById(R.id.transactionValue);
        createdBy = (EditText) findViewById(R.id.transactionCreator);
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        selectedTrans = args.getParcelable("THIS_TRANSACTION");
        description.setText(selectedTrans.getDescription(), TextView.BufferType.NORMAL);
        title.setText(selectedTrans.getTitle(), TextView.BufferType.NORMAL);
        value.setText(Integer.toString(selectedTrans.getValueOfT()), TextView.BufferType.NORMAL);
        String c = selectedTrans.getPayee().substring(9);
        createdBy.setText(c);
        collectiveId = getIntent().getStringExtra("CURRENT_COLLECTIVE_ID");
        dbRef = FirebaseDatabase.getInstance().getReference();
        backToTransactions.setOnClickListener(this);
        saveChanges.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        transactionListView = new Intent(TransactionView.this, CollectiveViewActivity.class);
        transactionListView.putExtra("CURRENT_COLLECTIVE_ID", collectiveId);
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

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings){
            settingsIntent.putExtra("CURRENT_COLLECTIVE_ID", collectiveId);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

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
        selectedTrans.setDescription(description.getText().toString().trim());
        selectedTrans.setTitle(title.getText().toString().trim());
        selectedTrans.setValueOfT(Integer.parseInt(value.getText().toString().trim()));
        dbRef.child("collectives/" + collectiveId +"/transactions/" + (Integer.toString(getIntent().getIntExtra("ARRAY_POSITION", -1)))).setValue(selectedTrans);
        Toast.makeText(TransactionView.this,"Transaction Edited", Toast.LENGTH_SHORT).show();
    }

}
