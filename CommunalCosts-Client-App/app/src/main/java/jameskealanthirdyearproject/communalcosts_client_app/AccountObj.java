package jameskealanthirdyearproject.communalcosts_client_app;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by kealan on 05/02/18.
 */

public class AccountObj {

    private String name;
    private String email;
    private String uid;
    private ArrayList<String> collectives;

    public AccountObj(){}

    public AccountObj(String n, String e){
        this.name = n;
        this.email = e;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
