package jameskealanthirdyearproject.communalcosts_client_app;

/**
 * Created by C5228122 on 23/02/2018.
 */

public class myPairObj {

    private String usrPermission;
    private String usrEmail;
    private String usrDisplayName;

    public myPairObj(){}
    public myPairObj(String usrEmail){
        this.usrPermission = "ordinary";
        this.usrEmail = usrEmail;
    }
    public myPairObj(String usrPermission, String usrEmail){
        this.usrPermission = usrPermission;
        this.usrEmail = usrEmail;
    }
    public String getUsrPermission() {
        return usrPermission;
    }
    public void setUsrPermission(String usrPermission) {
        this.usrPermission = usrPermission;
    }
    public String getUsrEmail() {
        return usrEmail;
    }
    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }
    public String getusrDisplayName() {
        return usrDisplayName;
    }

    public void setusrDisplayName(String userName) {
        this.usrDisplayName = userName;
    }
}
