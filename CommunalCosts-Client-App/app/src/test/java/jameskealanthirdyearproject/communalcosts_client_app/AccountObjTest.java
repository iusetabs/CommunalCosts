package jameskealanthirdyearproject.communalcosts_client_app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kealan on 05/03/18.
 */
public class AccountObjTest {

    private AccountObj testAccount;

    @Before
    public void setUp() throws Exception {
        testAccount = new AccountObj("test", "test@mail.com");

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName() throws Exception {
        assertTrue(testAccount.getName().equals("test"));
    }

    @Test
    public void setName() throws Exception {
        testAccount.setName("new name");
        assertTrue(testAccount.getName().equals("new name"));
    }

    @Test
    public void getEmail() throws Exception {
        assertTrue(testAccount.getEmail().equals("test@mail.com"));
    }

    @Test
    public void setEmail() throws Exception {
        testAccount.setEmail("new@email.test");
        assertTrue(testAccount.getEmail().equals("new@email.test"));
    }

}