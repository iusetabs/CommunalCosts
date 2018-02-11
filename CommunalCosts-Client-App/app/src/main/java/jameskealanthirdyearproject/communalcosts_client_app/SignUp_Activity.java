package jameskealanthirdyearproject.communalcosts_client_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class SignUp_Activity extends AppCompatActivity implements View.OnClickListener {

    private Button SignUpBtn;
    private EditText EmailF;
    private EditText PassF;
    private ProgressDialog pD;
    private FirebaseAuth firAuth;
    private Intent home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        home = new Intent(SignUp_Activity.this, Home_Activity.class);
        firAuth = FirebaseAuth.getInstance();
        pD = new ProgressDialog(this);
        SignUpBtn = (Button) findViewById(R.id.SignUp_SignUpBtn);
        EmailF = (EditText) findViewById(R.id.SignUp_EmailField);
        PassF = (EditText) findViewById(R.id.SignUp_PasswordField);
        SignUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == SignUpBtn){
            registerUser();
        }
    }

    private void registerUser() {
// FIXME: 11/02/18 19:00 Sign up feature not working on api 19, returning failed sign up message

        String email = EmailF.getText().toString().trim();
        String password = PassF.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(SignUp_Activity.this,"Please enter Email", Toast.LENGTH_SHORT).show();
            return; //stop function being executed
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(SignUp_Activity.this,"Please enter Password", Toast.LENGTH_SHORT).show();
            return; //stopping function being executed
        }

        pD.setMessage("Registering your account.");
        pD.show();

        firAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pD.dismiss();
                if(task.isSuccessful()){
                    finish();
                    startActivity(home);
                }else{
                    Toast.makeText(SignUp_Activity.this,"Failed to Sign Up", Toast.LENGTH_SHORT).show();
                    //unsuccessful register
                }
            }
        });
    }
}
