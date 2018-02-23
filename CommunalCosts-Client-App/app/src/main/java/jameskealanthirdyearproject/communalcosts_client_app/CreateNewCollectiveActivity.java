package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.nfc.FormatException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
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

/**
 * Created by kealan on 12/02/18.
 */

public class CreateNewCollectiveActivity extends AppCompatActivity implements View.OnClickListener{

    private Button returnBtn, createBtn, addMemBtn, rmMemBtn;
    private Intent homeScreenActv;
    private TextView memsView, memPermsView;
    private EditText colName, colType, colMembers, colID, colMembersPerm;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    private Intent logInActivity, collectiveView;
    private CollectiveObj mCollective;
    private String email;

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
        rmMemBtn = (Button) findViewById(R.id.colCreate_rmBtn);
        colName = (EditText) findViewById(R.id.colCreate_nameF);
        colType = (EditText) findViewById(R.id.colCreate_typeF);
        colID = (EditText) findViewById(R.id.colCreate_colIDF);
        colMembers = (EditText) findViewById(R.id.colCreate_addMembersF);
        colMembersPerm = (EditText) findViewById(R.id.colCreate_addMemberPermissionF);
        addMemBtn = (Button) findViewById(R.id.colCreate_addMemberBtn);
        memsView = (TextView) findViewById(R.id.colCreate_displayAddedMembers);
        memPermsView = (TextView) findViewById(R.id.colCreate_displayAddedMemberPermissions);

        createBtn.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
        addMemBtn.setOnClickListener(this);
        rmMemBtn.setOnClickListener(this);

        mCollective = new CollectiveObj();

        homeScreenActv = new Intent(CreateNewCollectiveActivity.this, HomeCollectiveView.class);
        logInActivity = new Intent(CreateNewCollectiveActivity.this, LogInActivity.class);
        collectiveView = new Intent(CreateNewCollectiveActivity.this, CollectiveViewActivity.class);
    }

    @Override
    public void onClick(View v) {
        if (v == returnBtn){
            finish();
            startActivity(homeScreenActv);
        }
        else if (v == createBtn){
            if(colName.getText().length() < 1 || colType.getText().length() < 1 || colID.getText().length() < 1){ //no need to add members here
                Toast.makeText(CreateNewCollectiveActivity.this, "Please fill in empty fields", Toast.LENGTH_SHORT).show();
            }
            else {
                createCol();
                finish();
                collectiveView.putExtra("CURRENT_COLLECTIVE_ID", mCollective.getCollectiveId() );
                startActivity(collectiveView);
            }
        }
        else if (v == addMemBtn){
            email = colMembers.getText().toString().trim();
            String permission = colMembersPerm.getText().toString().trim();
            boolean valid = (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
            if(valid) {
                if(permission.length() > 7 )
                    mCollective.addMember(email, permission);
                else {
                    mCollective.addMember(email);
                    permission = "ordinary";
                }
                memsView.append(email + "\n");
                memPermsView.append(permission + "\n");
            }
            else{
                Toast.makeText(CreateNewCollectiveActivity.this,"Email address invalid", Toast.LENGTH_SHORT).show();
            }
            memsView.computeScroll();
            colMembers.setText("");
            colMembersPerm.setText("");
        }
        else if (v == rmMemBtn){
            mCollective.rmMember(email);
            memsView.setText("");
            memPermsView.setText("");
            myPairObj p = new myPairObj();
            for(int i = 0; i < mCollective.getMembersLength(); i++){
                p = mCollective.getMemberAt(i);
                memsView.append(p.getUsrEmail() + "\n");
                memPermsView.append(p.getUsrPermission() + "\n");
            }
        }
    }
    private void createCol() {
        FirebaseUser userRef = firebaseAuth.getCurrentUser();
        mCollective.setCreator(userRef.getUid());
        mCollective.addMember(userRef.getEmail(), "creator"); //add creator first to members

        mCollective.setCollectiveName(colName.getText().toString().trim());
        mCollective.setCollectiveType(colType.getText().toString().trim());
        mCollective.setCollectiveId(colID.getText().toString().trim());

        dbRef.child("collectives").child(mCollective.getCollectiveId()).setValue(mCollective);
        Toast.makeText(CreateNewCollectiveActivity.this,"Sucess! Created!", Toast.LENGTH_SHORT).show();
    }
}
