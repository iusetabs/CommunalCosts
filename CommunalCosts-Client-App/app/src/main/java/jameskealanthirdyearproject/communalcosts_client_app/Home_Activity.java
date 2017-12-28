package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home_Activity extends AppCompatActivity implements View.OnClickListener{

    private Button logOutBtn;
    private FirebaseAuth firAuth;
    private Intent logInActivity;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        logInActivity = new Intent(Home_Activity.this, LogIn_Activity.class);
        logOutBtn = (Button) findViewById(R.id.Home_LogOutBtn);

        welcomeText = (TextView) findViewById(R.id.Home_WelcomeField);

        firAuth = FirebaseAuth.getInstance();
        if(firAuth.getCurrentUser() == null){
            finish();
            startActivity(logInActivity);
        }

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
    }
}
