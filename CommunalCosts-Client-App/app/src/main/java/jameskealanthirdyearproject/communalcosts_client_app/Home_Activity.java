package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home_Activity extends AppCompatActivity implements View.OnClickListener{

    private Intent editDetailsBtn;
    private Button edit;
    private Button logOutBtn;
    private Button joinCollBtn;
    private FirebaseAuth firAuth;
    private Intent logInActivity;
    private Intent colActivity;
    private TextView welcomeText;
    private AlertDialog.Builder addCollectiveDia;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        edit = (Button) findViewById(R.id.home_chng_detailsBtn);
        editDetailsBtn = new Intent(Home_Activity.this, accountDetails.class);
        logInActivity = new Intent(Home_Activity.this, LogIn_Activity.class);
        colActivity = new Intent(Home_Activity.this, CommunalActivity.class);
        logOutBtn = (Button) findViewById(R.id.Home_LogOutBtn);
        joinCollBtn = (Button) findViewById(R.id.home_JoinCollectiveBtn);

        welcomeText = (TextView) findViewById(R.id.Home_WelcomeField);

        firAuth = FirebaseAuth.getInstance();
        if(firAuth.getCurrentUser() == null){
            finish();
            startActivity(logInActivity);
        }

        edit.setOnClickListener(this);
        joinCollBtn.setOnClickListener(this);
        FirebaseUser user = firAuth.getCurrentUser();
        welcomeText.setText("Welcome " + user.getEmail());
        logOutBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == logOutBtn){
            //log out
            firAuth.signOut();
            finish();
            startActivity(logInActivity);
        }
        else if (v == joinCollBtn){
            addCollectiveDia = new AlertDialog.Builder(Home_Activity.this);
            mView = getLayoutInflater().inflate(R.layout.home_activity_addcol, null);
            final EditText colIDF = (EditText) mView.findViewById(R.id.homeAddCol_colIDField);
            final Button joinCol = (Button) mView.findViewById(R.id.homeAddCol_joinColBtn);
            final Button createCol = (Button) mView.findViewById(R.id.homeAddCol_createColBtn);
            joinCol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(colActivity);

                }
            });
            createCol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            addCollectiveDia.setView(mView);
            AlertDialog dialog = addCollectiveDia.create();
            dialog.show();
        }
        else if (v==edit){
            finish();
            startActivity(editDetailsBtn);

        }
    }
}
