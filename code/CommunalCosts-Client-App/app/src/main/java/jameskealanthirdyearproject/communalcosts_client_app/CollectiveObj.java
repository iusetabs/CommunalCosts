package jameskealanthirdyearproject.communalcosts_client_app;
import java.util.ArrayList;
import java.util.HashMap;


import static android.content.ContentValues.TAG;

/**
 * Created by kealan on 07/02/18.
 */

public class CollectiveObj {

    /*We need to clean up this file - it's kind of messy! */
    private ArrayList<TransactionObj> transactions = new ArrayList<>();
    private ArrayList<myPairObj> members = new ArrayList<>();
    private String collectiveName;
    private String collectiveId;
    private String collectiveType;
    private String creator;

    public CollectiveObj(){

    }
    public CollectiveObj(String name, String type, String id, String c){ //created solely for test files
        this.collectiveId = id;
        this.collectiveType = type;
        this.collectiveName = name;
        this.creator = c;
    }
    public CollectiveObj(String name, String type, String id, String c, ArrayList<myPairObj> mems){
        this.collectiveId = id;
        this.collectiveType = type;
        this.collectiveName = name;
        this.creator = c;
        this.members = mems;
    }
    /*MemberFunctions*/
    public void addMember(String userEmail) {
        myPairObj pos1 = new myPairObj("ordinary", userEmail);
        myPairObj pos2 = new myPairObj("administrator", userEmail);
        myPairObj pos3 = new myPairObj("contributor", userEmail);
        if(!this.members.contains(pos1) && !this.members.contains(pos2) && !this.members.contains(pos3)) {
            myPairObj member = new myPairObj(userEmail);
            this.members.add(member);
        }
    }
    public void addMember(String userEmail, String permission) {
        myPairObj pos1 = new myPairObj("ordinary", userEmail);
        myPairObj pos2 = new myPairObj("administrator", userEmail);
        myPairObj pos3 = new myPairObj("contributor", userEmail);
        if(!this.members.contains(pos1) && !this.members.contains(pos2) && !this.members.contains(pos3)) {
            myPairObj member = new myPairObj(permission, userEmail);
            this.members.add(member);
        }
    }
    public void rmMember(String userEmail){
        myPairObj p;
        for(int i = 0; i < this.members.size(); i++){
            p = this.members.get(i);
            if(p.getUsrEmail() == userEmail){
                this.members.remove(i);
                break;
            }
        }
    }
    public myPairObj getMemberAt(int i){
        return this.members.get(i);
    }
    public int getMembersLength(){
        return this.members.size();
    }

    public ArrayList<myPairObj> getMembers() {
        return this.members;
    }

    public ArrayList<TransactionObj> getTransactions() {
        return this.transactions;
    }

    public void addTransaction(TransactionObj transaction) {
        this.transactions.add(transaction);
    }

    public void removeTransaction(TransactionObj transactionObj){
        this.transactions.remove(transactionObj);
    }
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCollectiveName() {
        return collectiveName;

    }
    public String getCollectiveType() {
        return collectiveType;
    }

    public void setCollectiveType(String collectiveType) {
        this.collectiveType = collectiveType;
    }
    public void setCollectiveName(String collectiveName) {
        this.collectiveName = collectiveName;
    }

    public String getCollectiveId() {
        return collectiveId;
    }

    public void setCollectiveId(String collectiveId) {
        this.collectiveId = collectiveId;
    }
}
