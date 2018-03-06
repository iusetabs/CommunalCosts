package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by C5228122 on 06/03/2018.
 */
public class HomeCollectiveViewTest{
    @Rule
    public ActivityTestRule<HomeCollectiveView> mTRule = new ActivityTestRule<>(HomeCollectiveView.class);
    private HomeCollectiveView mActv = null;
    private final String TAG = HomeCollectiveView.class.getSimpleName();
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private FirebaseAuth firAuth;
    private FirebaseUser userRef;

    @Before
    public void setUp() throws Exception {
        mActv = mTRule.getActivity();
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference();
        firAuth = FirebaseAuth.getInstance();
        userRef = firAuth.getCurrentUser();
        if(userRef == null){
            firAuth.signInWithEmailAndPassword("james.nolan38@mail.dcu.ie", "ini123").addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    final AuthResult result = task.getResult();
                    userRef = result.getUser();
                }
            });
        }
    }

/*--------------------------------TEST-METHODS----------------------------------------*/
    @Test
    public void actvIsNotNull() throws NullPointerException{
        assertNotNull("HomeCollectiveView is null...", mActv);
    }

    @Test
    public void testElems() throws Resources.NotFoundException{
        View v1 = mActv.findViewById(R.id.collectiveListView);
        View v2 = mActv.findViewById(R.id.appBarLayout);
        View v3 = mActv.findViewById(R.id.toolbar);
        View v4 = mActv.findViewById(R.id.logoutBtn);
        View v5 = mActv.findViewById(R.id.addCollectiveBtn);
        View v6 = mActv.findViewById(R.id.coordinatorLayout2);
        View [] array = {v1, v2, v3, v4, v5, v6};
        for(int i = 0; i < array.length; i++){
            Log.d(TAG, "Array[" + i + "] is " + array[i]);
            assertNotNull("View_"+ i + " is null...", array[i]);
        }
        /*--------------------------------------------------------------------------------------------
                                            ------OUTPUT------
        Array[0] is android.widget.ListView{a8e6db4 V.ED.VC.. ........ -29,116-751,1136 #7f090047 app:id/collectiveListView}
        Array[1] is android.support.constraint.ConstraintLayout{149bddd V.E...... .......D -40,0-734,112 #7f09002a app:id/appBarLayout}
        Array[2] is android.support.v7.widget.Toolbar{a2d5d52 V.E...... ........ 0,0-774,112 #7f0900d6 app:id/toolbar}
        Array[3] is android.support.v7.widget.AppCompatButton{72be823 VFED..C.. .F....ID 32,8-208,104 #7f09007e app:id/logoutBtn}
        Array[4] is android.support.design.widget.FloatingActionButton{c7eb120 VFED..C.. ........ 579,992-691,1104 #7f090023 app:id/addCollectiveBtn}
        Array[5] is android.support.constraint.ConstraintLayout{aa95ad9 V.E...... .......D 0,0-720,1136 #7f09004d app:id/coordinatorLayout2}
         --------------------------------------------------------------------------------------------*/
    }

    @Test
    public void onCreate() throws Exception {
    }

    @Test
    public void test_FirebaseWriting() throws Exception{
        assertNotNull("The Database instance is null...", db);
        assertNotNull("The Database reference is null...", dbRef);
        assertNotNull("Firebase Auth is null...", firAuth);
        assertNotNull("Firebase User is null...", userRef);

        final String t1 = "Hello world";
        final String t2 = "LOL";
        final int val = 20;
        final boolean[] isError = {false};
        final AccountObj testAccount = new AccountObj("James Nolan", "james.nolan@dcu.ie");
        final CollectiveObj testCollective = new CollectiveObj("TestCol", "Test", "IDTest", "James");
        final myPairObj testPair = new myPairObj("Administrator", "james.nolan@dcu.ie");
        final TransactionObj testTransaction = new TransactionObj("This is a test", 20, "James");
        Log.d(TAG, "Beginning try method");
        new Thread(new Runnable() {
            public void run() {
                try {
                    dbRef.child("Testing/t1").setValue(t1);
                    Log.d(TAG, "Beginning to write to DB");
                    dbRef.child("Testing/t2").setValue(t2);
                    dbRef.child("Testing/val").setValue(val);
                    dbRef.child("Testing/testAccount").setValue(testAccount);
                    dbRef.child("Testing/testCollective").setValue(testCollective);
                    dbRef.child("Testing/testPair").setValue(testPair);
                    dbRef.child("Testing/testTransaction").setValue(testTransaction);
                } catch (Exception e) {
                    isError[0] = true;
                }
            }
        }).start();
        assertFalse("There was an excepition writing to DB...", isError[0]);
    }
    @Test
    public void test_FirebaseRead() throws Exception{
        assertNotNull("The Database instance is null...", db);
        assertNotNull("The Database reference is null...", dbRef);
        assertNotNull("Firebase Auth is null...", firAuth);
        assertNotNull("Firebase User is null...", userRef);
        final String t1 = "Hello world";
        final String t2 = "LOL";
        final int val = 20;
        final boolean[] isError = {false};
        final AccountObj testAccount = new AccountObj("James Nolan", "james.nolan@dcu.ie");
        final CollectiveObj testCollective = new CollectiveObj("TestCol", "Test", "IDTest", "James");
        final myPairObj testPair = new myPairObj("Administrator", "james.nolan@dcu.ie");
        final TransactionObj testTransaction = new TransactionObj("This is a test", 20, "James");

        new Thread(new Runnable() {
            public void run() {
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            assertEquals("t1 is incorrect...", t1, dataSnapshot.child("Testing/t1").getValue());
                            Log.d(TAG, "Database Value for t1:" + dataSnapshot.child("Testing/t1").getValue(String.class));
                            assertEquals("t2 is incorrect...", t2, dataSnapshot.child("Testing/t2").getValue());
                            assertSame("val is incorrect", val, dataSnapshot.child("Testing/val").getValue(Integer.class));
                            assertEquals("testAccount is incorrect", testAccount.getEmail(), dataSnapshot.child("Testing/testAccount").getValue(AccountObj.class).getEmail());
                            assertEquals("testCollective is incorrect...", testCollective.getCollectiveName(), dataSnapshot.child("Testing/testCollective").getValue(CollectiveObj.class).getCollectiveName());
                            assertEquals("testPair is incorrect...", testPair.getUsrEmail(), dataSnapshot.child("Testing/testPair").getValue(myPairObj.class).getUsrEmail());
                            assertEquals("testTransaction is incorrect...", testTransaction.getPayee(), dataSnapshot.child("Testing/testTransaction").getValue(TransactionObj.class).getPayee());

                        } catch (Exception e) {
                            isError[0] = true;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }).start();
        assertFalse("An error has occured reading from the DB...", isError[0]);

    }

    @Test
    public void onClick() throws Exception {
    }

    @Test
    public void getMyCollectives() throws Exception {
    }

    @Test
    public void getCollectivesList() throws Exception {
    }

    @Test
    public void test_subscribeDeviceToNotifications() throws Exception{
        ArrayList<String> t1 = new ArrayList<>();
        mActv.subscribeDeviceToNotifications(t1);
        t1.add("DONOTDELETE");
        mActv.subscribeDeviceToNotifications(t1);
        t1.add("Breakit");
        mActv.subscribeDeviceToNotifications(t1);
        Assert.fail("This should fail as the collective doesn't exist to subscribe to!"); //this should crash the app as the backend function should take care of this. We should throw an exceptition here in the main method.
    }

    @Test
    public void test_unSubscribeDeviceToNotifications() throws Exception{
        ArrayList<String> t1 = new ArrayList<>();

    }


    @Test
    public void checkGPlay() throws Exception {
        GoogleApiAvailability apiAva = GoogleApiAvailability.getInstance();
        int curVer = apiAva.isGooglePlayServicesAvailable(getContext()); //TODO: deploy this function to cloud to test on other devices

        assertThat("Device GServices not compatiable...",curVer, is(0));
        assertThat("Connection can't be achieved...", 0,is(ConnectionResult.SUCCESS));
        assertTrue("1 isn't resolvable...", apiAva.isUserResolvableError(1));
        assertTrue("2 isn't resolvable...", apiAva.isUserResolvableError(2));
        assertTrue("3 isn't resolvable...", apiAva.isUserResolvableError(3));
        assertFalse("4 isn't resolvable...", apiAva.isUserResolvableError(4));
    }
/*----------------------------------END-OF-TESTING--------------------------------------*/

    @After
    public void tearDown() throws Exception {
        mActv = null;
    }

}