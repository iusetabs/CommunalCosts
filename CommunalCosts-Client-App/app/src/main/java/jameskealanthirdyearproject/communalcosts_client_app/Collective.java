package jameskealanthirdyearproject.communalcosts_client_app;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by kealan on 07/02/18.
 */

public class Collective {
    private ArrayList<Pair<String, Integer>> members = new ArrayList<Pair<String, Integer>>();
    private String databaseFinancials;


    public Collective Collective(){
        this.members = new ArrayList<Pair<String, Integer>>();
    }

    public Collective add(Account user, Integer privilege) {
        if (privilege >= 0 & privilege <=2){
            this.members.add(user.getUid(), privilege);
        }
        else{
            System.out.print("invalid privilege level");
        }
    }
    /*we don't know how to add m*/
}
