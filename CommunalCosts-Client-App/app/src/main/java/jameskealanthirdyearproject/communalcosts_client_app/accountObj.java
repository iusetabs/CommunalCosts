package jameskealanthirdyearproject.communalcosts_client_app;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by kealan on 05/02/18.
 */

public class accountObj {

    private String name;
    private String email;
    private String dOB;
    private boolean emailVerified;
    private String uid;
    private ArrayList<String> collectives;

    public accountObj(){}

    public accountObj(String n, String e, String d){
        this.name = n;
        this.email = e;
        this.dOB = d;
    }

   /* public accountObj accountObj(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.name = user.getDisplayName();
        this.email = user.getEmail();
        this.emailVerified = user.isEmailVerified();
        this.uid = user.getUid();

        return this;
    }*/

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
