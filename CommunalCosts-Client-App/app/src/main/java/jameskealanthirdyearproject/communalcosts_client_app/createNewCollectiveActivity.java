package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by kealan on 12/02/18.
 */

public class createNewCollectiveActivity extends AppCompatActivity implements View.OnClickListener{

    private Button returnBtn, createBtn, addMemBtn;
    private Intent homeScreenActv;
    private TextView memsView;
    private EditText colName, colType, colMembers;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    private Intent logInActivity;
    private collectiveObj mCollective;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_collective);

        dbRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(logInActivity);
        }
        returnBtn = (Button) findViewById(R.id.colCreate_backBtn);
        createBtn = (Button) findViewById(R.id.colCreate_createBtn);
        colName = (EditText) findViewById(R.id.colCreate_nameF);
        colType = (EditText) findViewById(R.id.colCreate_typeF);
        colMembers = (EditText) findViewById(R.id.colCreate_addMembersF);
        addMemBtn = (Button) findViewById(R.id.colCreate_addMemberBtn);
        memsView = (TextView) findViewById(R.id.colCreate_displayAddedMembers);

        createBtn.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
        addMemBtn.setOnClickListener(this);

        mCollective = new collectiveObj();

        homeScreenActv = new Intent(createNewCollectiveActivity.this, Home_Activity.class);
        logInActivity = new Intent(createNewCollectiveActivity.this, LogIn_Activity.class);

    }
    @Override
    public void onClick(View v) {
        if (v == returnBtn){
            finish();
            startActivity(homeScreenActv);
        }
        else if (v == createBtn){
            createCol();
        }
        else if (v == addMemBtn){ /*Please insert the String checker here. Check for "@" char in the string and ensure the string length is greater than or equal to 5 a@a.a is the shortest valid email*/
                mCollective.addMembers(colMembers.getText().toString().trim());
                memsView.append(colMembers.getText().toString().trim() + "\n" );
                memsView.computeScroll();
                colMembers.setText("");
        }
    }
    private void createCol() {
        FirebaseUser userRef = firebaseAuth.getCurrentUser();
        mCollective.setCollectiveName(colName.getText().toString().trim());
        mCollective.setCollectiveId(colType.getText().toString().trim());
        mCollective.setCreator(userRef.getUid().toString()); //this is the primary key for the user, even if they change email we can still find their email
        dbRef.child("collectives").child(mCollective.getCollectiveName()).setValue(mCollective);
        Toast.makeText(createNewCollectiveActivity.this,"User info updated", Toast.LENGTH_SHORT).show();
    }
}
