package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AddTransaction extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText description, val, title;
    private TextView payee, currency, whoPaysView, spinView;
    private FloatingActionButton createTransaction;
    private Spinner memberChoices, photoChoices;
    private DatabaseReference dbRef;
    private TransactionObj myTransaction;
    private FirebaseAuth firebaseAuth;
    private Intent transactionView;
    private DataSnapshot collectiveSnapshot;
    private String collectiveId;
    private FirebaseUser userRef;
    private List<String> colDisplayNames;
    private List<String> copy;
    public static final String TAG = AddTransaction.class.getSimpleName();
    private ArrayAdapter<String> dataAdapter, fotoAdapter;
    private FirebaseStorage storage;
    private StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_add_transaction);
        collectiveId = getIntent().getStringExtra("CURRENT_COLLECTIVE_ID");

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        StorageReference ref = storageRef.child("images");


        dbRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userRef = firebaseAuth.getCurrentUser();

        description = (EditText) findViewById(R.id.transactionDetails);
        payee = (TextView) findViewById(R.id.transactionSource);
        spinView = (TextView) findViewById(R.id.spinnerView);
        whoPaysView = (TextView) findViewById(R.id.whoPaysView);
        whoPaysView.setMovementMethod(new ScrollingMovementMethod());
        val = (EditText) findViewById(R.id.value);
        title = (EditText) findViewById(R.id.transactionTitle);
        currency = (TextView) findViewById(R.id.transactionCurrency);
        photoChoices = (Spinner) findViewById(R.id.transactionSpinner2);
        photoChoices.setPrompt("Select an option");
        memberChoices = (Spinner) findViewById(R.id.transactionSpinner);
        memberChoices.setPrompt("Select from members");

        createTransaction = (FloatingActionButton) findViewById(R.id.CreateTransaction);
        createTransaction.setOnClickListener(this);
        transactionView = new Intent(AddTransaction.this, CollectiveViewActivity.class);
        myTransaction = new TransactionObj();

        photoChoices.setOnItemSelectedListener(this);
        memberChoices.setOnItemSelectedListener(this);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "DBListnerHit");
                collectiveSnapshot = dataSnapshot;
                colDisplayNames = new ArrayList<>();
                colDisplayNames.add("");
                colDisplayNames.add("Undo");
                colDisplayNames.add("All Members");
                for(DataSnapshot child : dataSnapshot.child("collectives/" + collectiveId + "/members").getChildren()){
                    Log.d(TAG, "Added: " + child.getValue(myPairObj.class).getusrDisplayName());
                    colDisplayNames.add(child.getValue(myPairObj.class).getusrDisplayName());
                }
                copy = copyAL(colDisplayNames);
                dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, colDisplayNames);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                memberChoices.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Failed to read value.", databaseError.toException());
            }
        });
        List<String> photoChoiceItems = new ArrayList<>();
        photoChoiceItems.add("Add a photo!");
        photoChoiceItems.add("From camera");
        photoChoiceItems.add("I already took one");
        fotoAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, photoChoiceItems);
        fotoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        photoChoices.setAdapter(fotoAdapter);

        dbRef.child("users").child(userRef.getUid()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                payee.setText("Added by: " + dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (parent.getId() == R.id.transactionSpinner) {
            // On selecting a spinner item
            Log.d(TAG, "Selected: " + item);
            if (!item.equals("")) {
                if (item.equals("All Members")) {
                    spinView.setText("All Members");
                    colDisplayNames.remove("All Members");
                    for (int i = 2; i < colDisplayNames.size(); i++) {
                        whoPaysView.append(colDisplayNames.get(i) + "\n");
                        myTransaction.addToYouOweMe(colDisplayNames.get(i));
                    }
                    colDisplayNames.clear();
                    colDisplayNames.add("");
                    colDisplayNames.add("Undo");
                } else if (item.equals("Undo")) {
                    Log.d(TAG, "Undo operation");
                    colDisplayNames = copyAL(copy);
                    spinView.setText("Undo");
                    myTransaction.removeAllOweMe();
                    whoPaysView.setText("");

                } else {
                    spinView.setText(item);
                    Log.d(TAG, "Added Item");
                    whoPaysView.append(item + "\n");
                    myTransaction.addToYouOweMe(item);
                    colDisplayNames.remove(item);
                    if (colDisplayNames.contains("All Members")) {
                        colDisplayNames.remove("All Members");
                    }
                }
                dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, colDisplayNames);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                memberChoices.setAdapter(dataAdapter);
            } else
                Log.d(TAG, "Blank will not be added");

        }
        else if (parent.getId() == R.id.transactionSpinner2){
            if(item.equals("From camera")){
                Intent camIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(camIntent);
            }
            else if(item.equals("I already took one")){
                Intent galIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galIntent, "Select File"),1);
            }
        }
    }

    private List<String> copyAL(List<String> copy) {
        List<String> a = new ArrayList<>();
        a.addAll(copy);
        return a;
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void createTransaction(DataSnapshot dataSnapshot) {//add datasnapshot here to grab the collective object from add the transaction to and update it
        myTransaction.setTitle(title.getText().toString().trim());
        myTransaction.setDescription(description.getText().toString().trim());
        myTransaction.setCreator(firebaseAuth.getUid());
        myTransaction.setPayee(payee.getText().toString().trim());
        try {
            myTransaction.setValueOfT(Integer.parseInt(val.getText().toString().trim()));
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
