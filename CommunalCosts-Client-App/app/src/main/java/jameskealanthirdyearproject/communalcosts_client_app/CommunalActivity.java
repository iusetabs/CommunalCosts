package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kealan on 12/02/18.
 */

public class CommunalActivity extends AppCompatActivity implements View.OnClickListener{

    private Button returnButt, updateButt;
    private Intent homeScreenActv;
    private EditText collectiveName, collectiveId, expense, expensei;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    private Intent logInActivity;
    private FirebaseUser usr;
    Collective myColl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_communal_finances);

        myColl = new Collective();
        collectiveName = (EditText) findViewById(R.id.commName);
        collectiveId = (EditText) findViewById(R.id.commId);
        expense = (EditText) findViewById(R.id.expense1);
        expensei = (EditText) findViewById(R.id.expense2);

        dbRef = FirebaseDatabase.getInstance().getReference();

        returnButt = (Button) findViewById(R.id.backBttn);
        updateButt = (Button) findViewById(R.id.refreshBttn);
        returnButt.setOnClickListener(this);
        updateButt.setOnClickListener(this);

        homeScreenActv = new Intent(CommunalActivity.this, Home_Activity.class);
        logInActivity = new Intent(CommunalActivity.this, LogIn_Activity.class);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(logInActivity);
        }
        usr = firebaseAuth.getCurrentUser();
        collectiveName.setText(usr.getDisplayName());

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               displayCollective(dataSnapshot);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayCollective(DataSnapshot dataSnapshot) {
        //CVObj cvU = new CVObj();
        myColl.setCollectiveName(dataSnapshot.child(usr.getUid()).getValue(Collective.class).getCollectiveName());
        collectiveName.setText(myColl.getCollectiveName(), TextView.BufferType.EDITABLE);

        myColl.setCollectiveId(dataSnapshot.child(usr.getUid()).getValue(Collective.class).getCollectiveId());
        collectiveId.setText(myColl.getCollectiveId(), TextView.BufferType.EDITABLE);
    }

    @Override
    public void onClick(View v) {
        if (v == returnButt){
            finish();
            startActivity(homeScreenActv);
        }
        else if (v == updateButt){
            updateCV();
        }
    }

    private void updateCV() {
        myColl.setCollectiveName(collectiveName.getText().toString().trim());
        myColl.setCollectiveId(collectiveId.getText().toString().trim());
        FirebaseUser user = firebaseAuth.getCurrentUser();
        dbRef.child(user.getUid()).setValue(myColl);
        Toast.makeText(CommunalActivity.this,"User info updated", Toast.LENGTH_SHORT).show();
    }
}
