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

import com.google.firebase.database.DataSnapshot;

public class TransactionView extends AppCompatActivity implements View.OnClickListener {

    private EditText description, origin, value;
    private Button saveChanges, backToTransactions;
    private Intent transactionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_view);

        saveChanges = (Button) findViewById(R.id.editBtn);
        backToTransactions = (Button) findViewById(R.id.backBtn);

        description = (EditText) findViewById(R.id.transactionDescription);
        origin = (EditText) findViewById(R.id.transactionPayee);
        value = (EditText) findViewById(R.id.transactionValue);

        description.setText(getIntent().getStringExtra("CURRENT_TRANSACTION_DESCRIPTION"), TextView.BufferType.NORMAL);
        origin.setText(getIntent().getStringExtra("CURRENT_TRANSACTION_PAYEE"), TextView.BufferType.NORMAL);
        Integer val = new Integer(0);
        value.setText(String.format("%d", getIntent().getIntExtra("CURRENT_TRANSACTION_VALUE", val)), TextView.BufferType.NORMAL);

        backToTransactions.setOnClickListener(this);
        saveChanges.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        transactionListView = new Intent(TransactionView.this, CollectiveViewActivity.class);

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
            // method to make database changes
        }
        else if (v == backToTransactions){
            finish();
            startActivity(transactionListView);
        }
    }


}
