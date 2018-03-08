package jameskealanthirdyearproject.communalcosts_client_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button LogInBtn;
    private Button SignUpBtn;
    private EditText UserF;
    private EditText PassF;
    private Intent home;
    private FirebaseAuth firAuth;
    private ProgressDialog pD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setTitle(R.string.login_welcome);

        home = new Intent(LogInActivity.this, HomeCollectiveView.class);
        pD = new ProgressDialog(this);
        firAuth = FirebaseAuth.getInstance();
        if(firAuth.getCurrentUser() != null){
            finish();
            startActivity(home);
        }

        LogInBtn = (Button) findViewById(R.id.LogIn_LogInBtn);
        SignUpBtn = (Button) findViewById(R.id.LogIn_SignUpBtn);
        UserF = (EditText) findViewById(R.id.LogIn_UserField);
        PassF = (EditText) findViewById(R.id.LogIn_PassField);

        LogInBtn.setOnClickListener(this);
        SignUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == LogInBtn){
            userLogin();

        }
        else if (v == SignUpBtn){
            finish();
            Intent i = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(i);
        }
    }

    private void userLogin() {

        String email = UserF.getText().toString().trim();
        String password = PassF.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(LogInActivity.this, R.string.toast_log_in_activity_email, Toast.LENGTH_SHORT).show();
            return; //stop function being executed
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(LogInActivity.this,R.string.toast_log_in_activity_passwd, Toast.LENGTH_SHORT).show();
            return; //stopping function being executed
        }

        pD.setMessage("Logging In. Please wait.");
        pD.show();
        firAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pD.dismiss();
                if(task.isSuccessful()){
                    //start profile activity
                    finish();
                    startActivity(home);
                }
                else{
                    Toast.makeText(LogInActivity.this,R.string.toast_invalid_log_in, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
