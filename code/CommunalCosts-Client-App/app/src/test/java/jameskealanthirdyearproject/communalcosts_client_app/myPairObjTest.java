package jameskealanthirdyearproject.communalcosts_client_app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by C5228122 on 05/03/2018.
 */
public class myPairObjTest {
    private myPairObj testObj = new myPairObj();
    @Test
    public void test_SetGetUsrPermission() throws Exception {
        assertNull("Permission = null", testObj.getUsrPermission());

        String t1 = "";
        testObj.setUsrPermission("");
        assertSame(t1, testObj.getUsrPermission());

        String t2 = " ";
        testObj.setUsrPermission(t2);
        assertSame(t2,testObj.getUsrPermission());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getUsrPermission());

        testObj.setUsrPermission(t3);
        assertSame(t3, testObj.getUsrPermission());
    }

    @Test
    public void test_SetGetUsrEmail() throws Exception {
        assertNull("UsrEmail = null", testObj.getUsrEmail());

        String t1 = "";
        testObj.setUsrEmail("");
        assertSame(t1, testObj.getUsrEmail());

        String t2 = " ";
        testObj.setUsrEmail(t2);
        assertSame(t2,testObj.getUsrEmail());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getUsrEmail());

        testObj.setUsrEmail(t3);
        assertSame(t3, testObj.getUsrEmail());
    }

    @Test
    public void test_SetGetusrDisplayName() throws Exception {
        assertNull("Display Name = null", testObj.getusrDisplayName());

        String t1 = "";
        testObj.setusrDisplayName("");
        assertSame(t1, testObj.getusrDisplayName());

        String t2 = " ";
        testObj.setusrDisplayName(t2);
        assertSame(t2,testObj.getusrDisplayName());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getusrDisplayName());

        testObj.setusrDisplayName(t3);
        assertSame(t3, testObj.getusrDisplayName());
    }

}