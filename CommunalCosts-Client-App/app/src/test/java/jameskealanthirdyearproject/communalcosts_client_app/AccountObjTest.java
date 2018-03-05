package jameskealanthirdyearproject.communalcosts_client_app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by C5228122 on 05/03/2018.
 */
public class AccountObjTest {
    private AccountObj testObj = new AccountObj();
    @Test
    public void test_SetGetName() throws Exception {
        assertNull("Name = null", testObj.getName());

        String t1 = "";
        testObj.setName("");
        assertSame(t1, testObj.getName());

        String t2 = " ";
        testObj.setName(t2);
        assertSame(t2,testObj.getName());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getName());

        testObj.setName(t3);
        assertSame(t3, testObj.getName());
    }

    @Test
    public void test_SetGetEmail() throws Exception {
        assertNull("Email = null", testObj.getEmail());

        String t1 = "";
        testObj.setEmail("");
        assertSame(t1, testObj.getEmail());

        String t2 = " ";
        testObj.setEmail(t2);
        assertSame(t2,testObj.getEmail());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getEmail());

        testObj.setEmail(t3);
        assertSame(t3, testObj.getEmail());
    }

    @Test
    public void test_SetGetUid() throws Exception {
        assertNull("User ID = null", testObj.getUid());

        String t1 = "";
        testObj.setUid("");
        assertSame(t1, testObj.getUid());

        String t2 = " ";
        testObj.setUid(t2);
        assertSame(t2,testObj.getUid());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getUid());

        testObj.setUid(t3);
        assertSame(t3, testObj.getUid());
    }

}