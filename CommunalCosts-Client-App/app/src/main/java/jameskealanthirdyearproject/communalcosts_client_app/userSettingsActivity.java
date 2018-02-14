package jameskealanthirdyearproject.communalcosts_client_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class accountDetails extends AppCompatActivity {

    private EditText userName, email;
    private accountObj myAccount;
    private FirebaseUser user;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        userName = (EditText) findViewById(R.id.displayUserName);
       // email = (EditText) findViewById(R.id.displayEmail); FIXME
        myAccount = new accountObj();
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayAccountDetails(dataSnapshot);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void displayAccountDetails(DataSnapshot dataSnapshot){
        myAccount.setEmail(dataSnapshot.child(user.getUid()).getValue(accountObj.class).getEmail());
        email.setText(myAccount.getEmail(), TextView.BufferType.EDITABLE);

        myAccount.setName(dataSnapshot.child(user.getUid()).getValue(accountObj.class).getName());
        userName.setText(myAccount.getName(), TextView.BufferType.EDITABLE);

    }


}
