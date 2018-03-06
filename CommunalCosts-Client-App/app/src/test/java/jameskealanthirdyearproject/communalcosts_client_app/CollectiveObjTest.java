package jameskealanthirdyearproject.communalcosts_client_app;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by C5228122 on 05/03/2018.
 */
public class CollectiveObjTest {
    CollectiveObj testObj = new CollectiveObj();

    /*------------------STRING METHODS------------------------------*/
    @Test
    public void test_SetGetCreator() throws Exception {
        assertNull("Creator = null", testObj.getCreator());

        String t1 = "";
        testObj.setCreator("");
        assertSame(t1, testObj.getCreator());

        String t2 = " ";
        testObj.setCreator(t2);
        assertSame(t2, testObj.getCreator());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getCreator());

        testObj.setCreator(t3);
        assertSame(t3, testObj.getCreator());
    }


    @Test
    public void test_SetGetCollectiveType() throws Exception {
        assertNull("Type = null", testObj.getCollectiveType());

        String t1 = "";
        testObj.setCollectiveType("");
        assertSame(t1, testObj.getCollectiveType());

        String t2 = " ";
        testObj.setCollectiveType(t2);
        assertSame(t2, testObj.getCollectiveType());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getCollectiveType());

        testObj.setCollectiveType(t3);
        assertSame(t3, testObj.getCollectiveType());
    }

    @Test
    public void test_SetGetCollectiveName() throws Exception {
        assertNull("Name = null", testObj.getCollectiveName());

        String t1 = "";
        testObj.setCollectiveName("");
        assertSame(t1, testObj.getCollectiveName());

        String t2 = " ";
        testObj.setCollectiveName(t2);
        assertSame(t2, testObj.getCollectiveName());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getCollectiveName());

        testObj.setCollectiveName(t3);
        assertSame(t3, testObj.getCollectiveName());
    }

    @Test
    public void test_SetGetCollectiveId() throws Exception {
        assertNull("ID = null", testObj.getCollectiveId());

        String t1 = "";
        testObj.setCollectiveId("");
        assertSame(t1, testObj.getCollectiveId());

        String t2 = " ";
        testObj.setCollectiveId(t2);
        assertSame(t2, testObj.getCollectiveId());

        String t3 = "abcdef12233";
        assertNotSame(t3, testObj.getCollectiveId());

        testObj.setCollectiveId(t3);
        assertSame(t3, testObj.getCollectiveId());
    }
/*--------------------------------------------------------------------------------------*/

/*---------------------------ARRAYLIST-myPairObj-Tests----------------------------------*/

    @Test
    public void test_addMemberWithoutPermission() throws Exception {
        ArrayList<myPairObj> testArray = new ArrayList<>();
        assertEquals(testArray, testObj.getMembers());

        myPairObj t1 = new myPairObj("a");
        myPairObj t2 = new myPairObj("b");
        testArray.add(t1);
        testArray.add(t2);
        assertNotEquals(testArray.size(), testObj.getMembers().size());


        testObj.addMember("a");
        testObj.addMember("b");
        assertSame(testArray.size(), testObj.getMembers().size());
        for (int i = 0; i < testArray.size(); i++) {
            assertSame(testArray.get(i).getUsrEmail(), testObj.getMembers().get(i).getUsrEmail());
        }
    }

    @Test
    public void test_addMemberWithPermission() throws Exception {
        ArrayList<myPairObj> testArray = new ArrayList<>();
        assertEquals(testArray, testObj.getMembers());

        myPairObj t1 = new myPairObj("administrator", "a");
        myPairObj t2 = new myPairObj("contributor", "b");
        testArray.add(t1);
        testArray.add(t2);
        assertNotEquals(testArray.size(), testObj.getMembers().size());

        testObj.addMember("a", "administrator");
        testObj.addMember("b", "contributor");
        assertSame(testArray.size(), testObj.getMembers().size());
        for (int i = 0; i < testArray.size(); i++) {
            assertSame(testArray.get(i).getUsrEmail(), testObj.getMembers().get(i).getUsrEmail());
        }
    }

    @Test
    public void rmMember() throws Exception {
        ArrayList<myPairObj> testArray = new ArrayList<>();
        assertEquals(testArray, testObj.getMembers());
        myPairObj t1 = new myPairObj("administrator", "a");
        testArray.add(t1);
        testObj.addMember("a", "administrator");
        assertSame(testArray.size(), testObj.getMembers().size());
        for (int i = 0; i < testArray.size(); i++) {
            assertSame(testArray.get(i).getUsrEmail(), testObj.getMembers().get(i).getUsrEmail());
        }

        testObj.rmMember("a");
        assertNotEquals(testArray, testObj.getMembers());

        testArray.remove(t1);
        assertEquals(testArray, testObj.getMembers());
    }

    @Test
    public void getMemberAt() throws Exception {
        ArrayList<myPairObj> testArray = new ArrayList<>();
        assertEquals(testArray, testObj.getMembers());
        myPairObj t1 = new myPairObj("administrator", "a");
        myPairObj t2 = new myPairObj("contributor", "b");

        testArray.add(t1);
        testArray.add(t2);
        testObj.addMember("a", "administrator");
        testObj.addMember("b", "contributor");
        assertSame(testArray.size(), testObj.getMembers().size());
        for (int i = 0; i < testArray.size(); i++) {
            assertSame(testArray.get(i).getUsrEmail(), testObj.getMemberAt(i).getUsrEmail());
        }
    }

    @Test
    public void getMembersLength() throws Exception {
        ArrayList<myPairObj> testArray = new ArrayList<>();
        assertEquals(testArray, testObj.getMembers());
        myPairObj t1 = new myPairObj("administrator", "a");
        myPairObj t2 = new myPairObj("contributor", "b");
        myPairObj t3 = new myPairObj("ordinary", "c");
        myPairObj t4 = new myPairObj("contributor", "d");

        testArray.add(t1);
        testArray.add(t2);
        testArray.add(t3);
        testArray.add(t4);
        testObj.addMember("a", "administrator");
        testObj.addMember("b", "contributor");
        assertNotSame(testArray.size(), testObj.getMembersLength());

        testObj.addMember("c", "ordinary");
        testObj.addMember("d", "contributor");
        assertSame(testArray.size(), testObj.getMembersLength());
    }

    @Test
    public void test_getMembers() throws Exception {
        ArrayList<myPairObj> testArray = new ArrayList<>();
        assertEquals(testArray, testObj.getMembers());
        myPairObj t1 = new myPairObj("administrator", "a");
        myPairObj t2 = new myPairObj("contributor", "b");
        myPairObj t3 = new myPairObj("ordinary", "c");
        myPairObj t4 = new myPairObj("contributor", "d");

        testArray.add(t1);
        testArray.add(t2);
        testArray.add(t3);
        testArray.add(t4);
        testObj.addMember("a", "administrator");
        testObj.addMember("b", "contributor");
        testObj.addMember("c", "ordinary");
        testObj.addMember("d", "contributor");
        ArrayList<myPairObj> comparArray = testObj.getMembers();
        assertSame(testArray.size(), testObj.getMembers().size());
        for (int i = 0; i < testArray.size(); i++) {
            assertSame(testArray.get(i).getUsrEmail(), comparArray.get(i).getUsrEmail());
            assertSame(testArray.get(i).getUsrPermission(), comparArray.get(i).getUsrPermission());
        }
    }
/*-----------------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------ARRAYLIST-TransactionObj-Tests---------------------------------------*/
    @Test
    public void test_addTransaction() throws Exception { //TODO some instument testing may be needed on this method
        ArrayList<TransactionObj> testArray = new ArrayList<>();
        assertEquals(testArray, testObj.getTransactions());

        TransactionObj t1 = new TransactionObj("a", 1, "you");
        TransactionObj t2 = new TransactionObj("b", 2, "me");
        testArray.add(t1);
        testArray.add(t2);
        assertNotSame(testArray.size(), testObj.getTransactions().size());
        testObj.addTransaction(t1);
        testObj.addTransaction(t2);
        for (int i = 0; i < testArray.size(); i++) {
            assertSame(testArray.get(i).getDescription(), testObj.getTransactions().get(i).getDescription());
            assertSame(testArray.get(i).getValueOfT(), testObj.getTransactions().get(i).getValueOfT());
        }
    }

    @Test
    public void test_removeTransaction() throws Exception {
        ArrayList<TransactionObj> testArray = new ArrayList<>();
        assertEquals(testArray, testObj.getTransactions());

        TransactionObj t1 = new TransactionObj("a", 1, "you");
        TransactionObj t2 = new TransactionObj("b", 2, "me");
        testArray.add(t1);
        testArray.add(t2);
        testObj.addTransaction(t1);
        testObj.addTransaction(t2);
        for (int i = 0; i < testArray.size(); i++) {
            assertSame(testArray.get(i).getDescription(), testObj.getTransactions().get(i).getDescription());
            assertSame(testArray.get(i).getValueOfT(), testObj.getTransactions().get(i).getValueOfT());

        }
        testObj.removeTransaction(t1);
        testArray.remove(t1);
        assertSame(testArray.size(), testObj.getTransactions().size());
        for (int i = 0; i < testArray.size(); i++) {
            assertSame(testArray.get(i).getDescription(), testObj.getTransactions().get(i).getDescription());
            assertSame(testArray.get(i).getValueOfT(), testObj.getTransactions().get(i).getValueOfT());

        }

    }
/*-------------------------------------------------------------------------------------------------------------------------*/
}