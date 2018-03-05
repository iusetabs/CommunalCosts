package jameskealanthirdyearproject.communalcosts_client_app;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by C5228122 on 05/03/2018.
 */
public class TransactionObjTest {

    final TransactionObj testObj = new TransactionObj();

    /*----------------Setters & Getters----------------- */
    @Test
    public void test_SetGetCreator() throws Exception {

        assertNull("Creator = null", testObj.getCreator());

        String t1 = "";
        testObj.setCreator("");
        assertSame(t1, testObj.getCreator());

        String t2 = " ";
        testObj.setCreator(t2);
        assertSame(t2,testObj.getCreator());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getCreator());

        testObj.setCreator(t3);
        assertSame(t3, testObj.getCreator());
    }

    @Test
    public void test_SetGetTitle() throws Exception {

        assertNull("Title = null", testObj.getTitle());

        String t1 = "";
        testObj.setTitle("");
        assertSame(t1, testObj.getTitle());

        String t2 = " ";
        testObj.setTitle(t2);
        assertSame(t2,testObj.getTitle());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getTitle());

        testObj.setTitle(t3);
        assertSame(t3, testObj.getTitle());
    }

    @Test
    public void test_SetGetEditedBy() throws Exception {
        assertNull("EditedBy = null", testObj.getEditedBy());

        String t1 = "";
        testObj.setEditedBy("");
        assertSame(t1, testObj.getEditedBy());

        String t2 = " ";
        testObj.setEditedBy(t2);
        assertSame(t2,testObj.getEditedBy());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getEditedBy());

        testObj.setEditedBy(t3);
        assertSame(t3, testObj.getEditedBy());
    }

    @Test
    public void test_SetGetId() throws Exception {
        assertNull("ID = null", testObj.getId());

        String t1 = "";
        testObj.setId("");
        assertSame(t1, testObj.getId());

        String t2 = " ";
        testObj.setId(t2);
        assertSame(t2,testObj.getId());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getId());

        testObj.setId(t3);
        assertSame(t3, testObj.getId());
    }

    @Test
    public void test_SetGetPayee() throws Exception {
        assertNull("Payee = null", testObj.getPayee());

        String t1 = "";
        testObj.setPayee("");
        assertSame(t1, testObj.getPayee());

        String t2 = " ";
        testObj.setPayee(t2);
        assertSame(t2,testObj.getPayee());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getPayee());

        testObj.setPayee(t3);
        assertSame(t3, testObj.getPayee());
    }

    @Test
    public void test_SetGetDescription() throws Exception {
        assertNull("Description = null", testObj.getDescription());

        String t1 = "";
        testObj.setDescription("");
        assertSame(t1, testObj.getDescription());

        String t2 = " ";
        testObj.setDescription(t2);
        assertSame(t2,testObj.getDescription());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getDescription());

        testObj.setDescription(t3);
        assertSame(t3, testObj.getDescription());
    }

    @Test
    //NOTE This is an int method//
    public void test_SetGetValueOfT() throws Exception {
        assertNull("ValueOfT = null", testObj.getValueOfT());

        int t1 = -10;
        testObj.setValue(t1);
        assertSame(t1, testObj.getValueOfT());

        int t2 = 100000;
        testObj.setValue(t2);
        assertSame(t2,testObj.getValueOfT());

        int t3 = 100;
        assertNotSame(t3, testObj.getValueOfT());

        testObj.setValue(t3);
        assertSame(t3, testObj.getValueOfT());
    }

    @Test
    //NOTE This is an array getter setter//
    public void test_SetGetYouOweMe() throws Exception {
        assertNull("YouOwwMe array = null", testObj.getYouOweMe());
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("1");
        testArray.add("2");
        testArray.add("3");
        testObj.setYouOweMe(testArray);
        assertSame(testArray, testObj.getYouOweMe());

        testArray.add("Hi There");
        testObj.setYouOweMe(testArray);
        assertSame(testArray,testObj.getYouOweMe());

        testArray.clear();
        assertNotSame(testArray, testObj.getYouOweMe());
    }

    @Test
    //NOTE This is an array getter setter//
    public void test_SetGetYouPaidMe() throws Exception {
        assertNull("YouPaidMe array = null", testObj.getYouPaidMe());
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("1");
        testArray.add("2");
        testArray.add("3");
        testObj.setYouPaidMe(testArray);
        assertSame(testArray, testObj.getYouPaidMe());

        testArray.add("Hi There");
        testObj.setYouPaidMe(testArray);
        assertSame(testArray,testObj.getYouPaidMe());

        testArray.clear();
        assertNotSame(testArray, testObj.getYouPaidMe());
    }

/*------------------------------------------------------------------------------*/

/*----------------------ArrayList Tests-----------------------------------------*/
    @Test
    public void updateValues() throws Exception {

    }
    @Test
    public void test_addToYouOweMe() throws Exception {
        assertNull(testObj.getYouOweMe());
        ArrayList<String> testArray = testObj.getYouOweMe();
        testArray.add("Addition");
        assertNotSame("Before method call arrays don't match", testArray, testObj.getYouOweMe());
        testObj.addToYouOweMe("Addition");
        assertSame("After method call arrays match", testArray, testObj.getYouOweMe());

    }
    @Test
    public void test_removeAllOweMe() throws Exception {
        assertNotNull("Before method call", testObj.getYouOweMe());
        testObj.removeAllOweMe();
        assertNull("After method call", testObj.getYouOweMe());
    }

}
/*------------------------------------------------------------------------------*/