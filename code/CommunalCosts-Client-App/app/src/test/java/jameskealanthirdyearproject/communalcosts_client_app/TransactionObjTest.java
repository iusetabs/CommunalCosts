package jameskealanthirdyearproject.communalcosts_client_app;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.doAnswer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


/**
 * Created by C5228122 on 05/03/2018.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class TransactionObjTest {

    private TransactionObj testObj = new TransactionObj();

    @Before
    public void before(){
        DatabaseReference mockedDatabaseReference = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);

    }


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
        testObj.setTitle(t1);
        assertSame(t1, testObj.getTitle());

        String t2 = "a";
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
        assertSame("ValueOfT = null", testObj.getValueOfT(), 0);

        int t1 = -10;
        testObj.setValueOfT(t1);
        assertSame(t1, testObj.getValueOfT());

        int t2 = 2;
        testObj.setValueOfT(t2);
        assertSame(t2, testObj.getValueOfT());

        int t3 = 100;
        assertNotSame(t3, testObj.getValueOfT());

        testObj.setValueOfT(t3);
        assertSame(t3, testObj.getValueOfT());
    }

    @Test
    //NOTE This is an array getter setter//
    public void test_SetGetYouOweMe() throws Exception {
        ArrayList<String> testArray = new ArrayList<>();
        assertEquals("YouOweMe array = []", testObj.getYouOweMe(), testArray);
        testArray.add("1");
        testArray.add("2");
        testArray.add("3");
        testObj.setYouOweMe(testArray);
        assertSame(testArray, testObj.getYouOweMe());

        testArray.add("Hi There");
        testObj.setYouOweMe(testArray);
        assertSame(testArray,testObj.getYouOweMe());
    }

    @Test
    //NOTE This is an array getter setter//
    public void test_SetGetYouPaidMe() throws Exception {
        ArrayList<String> testArray = new ArrayList<>();
        assertEquals("YouPaidMe array = null", testObj.getYouPaidMe(), testArray);

        testArray.add("1");
        testArray.add("2");
        testArray.add("3");
        testObj.setYouPaidMe(testArray);
        assertSame(testArray, testObj.getYouPaidMe());

        testArray.add("Hi There");
        testObj.setYouPaidMe(testArray);
        assertSame(testArray,testObj.getYouPaidMe());
    }

/*------------------------------------------------------------------------------*/

/*----------------------ArrayList Tests-----------------------------------------*/
/* TODO We may not need this method so not neccessary to test if this is the case!
    @Test
    public void test_updateValues() throws Exception {
        DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
        final TransactionObj expectedObj = new TransactionObj();
        expectedObj.setDescription("This is the description");
        expectedObj.setValueOfT(100);
        expectedObj.setPayee("This is a test payee");

        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);

                when(mockedDataSnapshot.exists()).thenReturn(true);
                when(mockedDataSnapshot.child("transactions").getValue(TransactionObj.class)).thenReturn(expectedObj);

                valueEventListener.onDataChange(mockedDataSnapshot);
                //valueEventListener.onCancelled(...);

                return null;
            }
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        when(mockedDataSnapshot.child("transactions").getValue(TransactionObj.class)).thenReturn(expectedObj);
        testObj.updateValues(mockedDataSnapshot);
        assertSame(expectedObj.getDescription(), testObj.getDescription());
        assertSame(expectedObj.getValueOfT(), testObj.getValueOfT());
        assertSame(expectedObj.getPayee(), testObj.getPayee());


    }*/
    @Test
    public void test_addToYouOweMe() throws Exception {

        ArrayList<String> testArray = new ArrayList<>();
        assertEquals(testObj.getYouOweMe(), testArray);

        testArray.add("Addition");
        assertNotSame("Before method call arrays don't match", testArray, testObj.getYouOweMe());

        testObj.addToYouOweMe("Addition");
        assertEquals("After method call arrays match", testArray, testObj.getYouOweMe());

    }
   @Test
    public void test_removeAllOweMe() throws Exception {
        ArrayList<String> testArray = new ArrayList<>();
        assertEquals("Before method call", testObj.getYouOweMe(), testArray);
        testObj.addToYouOweMe("hi");
        assertNotEquals(testObj.getYouOweMe(), testArray);
        testObj.removeAllOweMe();
        assertEquals("After method call", testObj.getYouOweMe(),testArray);
    }

}
/*------------------------------------------------------------------------------*/