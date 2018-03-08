package jameskealanthirdyearproject.communalcosts_client_app;

//import android.support.test.rule.ActivityTestRule;
//import static android.support.test.InstrumentationRegistry.getContext;
//import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by C5228122 on 04/03/2018.
 */
/*public class HomeCollectiveViewTest {
    @Rule
    public ActivityTestRule<HomeCollectiveView> mTRule = new ActivityTestRule<HomeCollectiveView>(HomeCollectiveView.class);
    private HomeCollectiveView mActv = null;

    @Before
    public void setUp() throws Exception {
        mActv = mTRule.getActivity();
    }
    @Test
    public void testLaunch(){
        View view = mActv.findViewById(R.id.addCollectiveBtn);
        assertNotNull(view);
    }
    @Test
    public void onCreate() throws Exception {
    }

    @Test
    public void onClick() throws Exception {
        final Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(LogInActivity.class.getName(), null, false);
        FloatingActionButton mAddColBtn = (FloatingActionButton) mActv.findViewById(R.id.addCollectiveBtn);
        final Button mLogOutBtn = (Button) mActv.findViewById(R.id.logoutBtn);
        mActv.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // click button and open next activity.
                try {
                    mLogOutBtn.performClick();
                    //Assert.fail("Should throw null pointer");
                }
                catch(NullPointerException e){
                    Log.d(TAG, "Null Pointer Caught");
                }
            }
        });
        LogInActivity logActv = (LogInActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 10000);
        assertNotNull(logActv);
        logActv.finish();
    }
    @Test
    public void getMyCollectives() throws Exception {

        final ArrayList<String> mMyCollectives = new ArrayList<>();
        /*    public ArrayList<String> getMyCollectives(DataSnapshot dataSnapshot){
        ArrayList<String> myCollectives = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("users/" + userRef.getUid() + "/myCollectives").getChildren()){
            String collectiveID = snapshot.getValue(String.class);
            myCollectives.add(collectiveID);
        }
        return myCollectives;
    }
    }

    @Test
    public void getCollectivesList() throws Exception {
    }

    @Test
    public void checkGPlay_ReturnsTrue() throws Exception {
        GoogleApiAvailability apiAva = GoogleApiAvailability.getInstance();
        int curVer = apiAva.isGooglePlayServicesAvailable(getContext());



        assertThat(curVer, is(0));
        assertThat(0,is(ConnectionResult.SUCCESS));
        assertTrue("1 is resolvable", apiAva.isUserResolvableError(1));
        assertTrue("2 is resolvable", apiAva.isUserResolvableError(2));
        assertTrue("3 is resolvable", apiAva.isUserResolvableError(3));
        assertFalse("4 is resolvable", apiAva.isUserResolvableError(4));

    }
    @After
    public void tearDown() throws Exception {
        mActv = null;
    }

}*/