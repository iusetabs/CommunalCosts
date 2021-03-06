package jameskealanthirdyearproject.communalcosts_client_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button SignUpBtn;
    private EditText EmailF;
    private EditText PassF;
    private ProgressDialog pD;
    private FirebaseAuth firAuth;
    private Intent home;
    private  EditText nameF;
    public DatabaseReference myDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.signup_activity);

        home = new Intent(SignUpActivity.this, HomeCollectiveView.class);

        firAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference();

        pD = new ProgressDialog(this);

        SignUpBtn = (Button) findViewById(R.id.SignUp_SignUpBtn);
        nameF = (EditText) findViewById(R.id.SignUp_NameField);
        EmailF = (EditText) findViewById(R.id.SignUp_EmailField);
        PassF = (EditText) findViewById(R.id.SignUp_PasswordField);
        SignUpBtn.setOnClickListener(this);

    }
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
    @Override
    public void onClick(View v) {
        if (v == SignUpBtn){
            registerUser();
        }
    }
    private void registerUser() {
// FIXME: 11/02/18 19:00 Sign up feature not working on api 19, returning failed sign up message
// FIXED: 12/02/18 15:00 There is a minimum password length of 4 characters

        String email = EmailF.getText().toString().trim();
        String password = PassF.getText().toString().trim();
        String name = nameF.getText().toString().trim();
        if(name.equals("")){
            Toast.makeText(SignUpActivity.this, R.string.signup_no_name, Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(email) || !(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            //email is empty
            Toast.makeText(SignUpActivity.this, R.string.signup_invalid_email, Toast.LENGTH_SHORT).show();
            return; //stop function being executed
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(SignUpActivity.this, R.string.toast_log_in_activity_passwd, Toast.LENGTH_SHORT).show();
            return; //stopping function being executed
        }
        pD.setMessage(getString(R.string.signup_in_progress));
        pD.show();

        firAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pD.dismiss();
                if(task.isSuccessful()){
                    FirebaseUser userRef = firAuth.getCurrentUser();
                    AccountObj userObj = new AccountObj(nameF.getText().toString().trim(), userRef.getEmail());
                    myDatabase.child("users").child(userRef.getUid()).setValue(userObj);
                    finish();
                    startActivity(home);
                }else{
                    Toast.makeText(SignUpActivity.this, R.string.signup_failed, Toast.LENGTH_SHORT).show();
                    //unsuccessful register
                }
            }
        });
    }
}
