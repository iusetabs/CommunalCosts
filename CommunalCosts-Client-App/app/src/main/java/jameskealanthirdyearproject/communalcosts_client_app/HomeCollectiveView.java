package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import jameskealanthirdyearproject.communalcosts_client_app.MyService.MyLocalBinder;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class HomeCollectiveView extends AppCompatActivity implements View.OnClickListener {

   // MyService myServ;]
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private ListView joinedCollectivesView;
    private CollectiveAdaptor adaptor;
    private FloatingActionButton addCollectiveButton;
    private Intent addCollective, logInActivity, testActivity, settingsIntent;
    private Button logOutBtn, testBtn; //testbutton added by james
    private FirebaseAuth firAuth;
    private FirebaseUser userRef;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private boolean isBound = false;
    private String tokenFCM =  FirebaseInstanceId.getInstance().getToken();
    private ArrayList<String> myCollectives;
    private AlertDialog.Builder addCollectiveDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_collective_view);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String updatedCollectiveName = sharedPreferences.getString("key_collective_name", "");
        System.out.println(updatedCollectiveName);

        settingsIntent = new Intent(HomeCollectiveView.this, SettingsActivity.class);

        if(checkGPlay()) { //register for GCM iff Play Services are compatible

            firAuth = FirebaseAuth.getInstance();
            userRef = firAuth.getCurrentUser();

            final ArrayList<CollectiveObj> collectiveList = new ArrayList<>();
            joinedCollectivesView = (ListView) findViewById(R.id.collectiveListView);
            adaptor = new CollectiveAdaptor(HomeCollectiveView.this, collectiveList);

            // final Intent i = new Intent(this, MyService.class); //starting service
            // bindService(i, myConnect, Context.BIND_AUTO_CREATE); //binding the service
            //startService(new Intent(getBaseContext(), MyService.class));

        /* put a button in settings that turns off notifications
        unbindService(myConnect);
        stopService(new Intent(getBaseContext(), MyService.class)); //stop the service*/

            db = FirebaseDatabase.getInstance();
            dbRef = db.getReference(); //FIXME this should only be listening to the users/userID/myCollectives area, presently it's listening to the entire database

            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    myCollectives = getMyCollectives(dataSnapshot);
                    subscribeDeviceToNotifications(myCollectives);
                    ArrayList<CollectiveObj> collectiveObjList = getCollectivesList(dataSnapshot);
                    for (CollectiveObj collectiveObj : collectiveObjList) {
                        if (myCollectives.contains(collectiveObj.getCollectiveId())) {
                            collectiveList.add(collectiveObj);
                        }
                    }
                    adaptor = new CollectiveAdaptor(HomeCollectiveView.this, collectiveList);
                    joinedCollectivesView.setAdapter(adaptor);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Failed to read value.", databaseError.toException());

                }
            });

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            addCollective = new Intent(HomeCollectiveView.this, CreateNewCollectiveActivity.class);
            addCollectiveButton = (FloatingActionButton) findViewById(R.id.addCollectiveBtn);
            addCollectiveButton.setOnClickListener(this);

            logOutBtn = (Button) findViewById(R.id.logoutBtn);
            logOutBtn.setOnClickListener(this);

            logInActivity = new Intent(HomeCollectiveView.this, LogInActivity.class);
            // adding click functionality
            final Context context = this;

            joinedCollectivesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //FIXME
                    CollectiveObj selectedCollective = collectiveList.get(position);
                    Intent detailIntent = new Intent(HomeCollectiveView.this, CollectiveViewActivity.class);
                    detailIntent.putExtra("CURRENT_COLLECTIVE_ID", selectedCollective.getCollectiveId());
                    startActivity(detailIntent);
                }
            });
        }
    }
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings){
            settingsIntent.putExtra("CURRENT_COLLECTIVE_ID", selectedCollective.getCollectiveId());
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/



    private void subscribeDeviceToNotifications(ArrayList<String> myCols) {
        for(int i = 0; i < myCols.size(); i++) {
            FirebaseMessaging.getInstance().subscribeToTopic(myCols.get(i)); //subscribe to messaging service
        }
    }
    private void unSubscribeDeviceToNotifications(ArrayList<String> myCols) {
        for(int i = 0; i < myCols.size(); i++) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(myCols.get(i)); //subscribe to messaging service
        }
    }

    @Override
    public void onClick(View v) {
        if (v == addCollectiveButton) {
            addCollectiveDia = new AlertDialog.Builder(HomeCollectiveView.this);
            View mView = getLayoutInflater().inflate(R.layout.home_activity_addcol, null);
            final EditText colIDF = (EditText) mView.findViewById(R.id.homeAddCol_colIDField);
            final Button joinCol = (Button) mView.findViewById(R.id.homeAddCol_joinColBtn);
            final Button createCol = (Button) mView.findViewById(R.id.homeAddCol_createColBtn);
            joinCol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //join the collective
                }
            });
            createCol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(addCollective);
                }
            });
            addCollectiveDia.setView(mView);
            AlertDialog dialog = addCollectiveDia.create();
            dialog.show();
        }
        else if (v == logOutBtn){
            Intent delFCM = new Intent(HomeCollectiveView.this, DeleteFCMTokenService.class);
            unSubscribeDeviceToNotifications(myCollectives);
            delFCM.putExtra("FCM_TOKEN", tokenFCM);
            startService(delFCM);
            firAuth.signOut();
            finish();
            startActivity(logInActivity);
        }
        else if (v == testBtn){ //added by james
            finish();
            startActivity(testActivity);
        }
    }

    public ArrayList<String> getMyCollectives(DataSnapshot dataSnapshot){
        ArrayList<String> myCollectives = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("users/" + userRef.getUid() + "/myCollectives").getChildren()){
            String collectiveID = snapshot.getValue(String.class);
            myCollectives.add(collectiveID);
        }
        return myCollectives;
    }

    public ArrayList<CollectiveObj> getCollectivesList(DataSnapshot dataSnapshot){ //FIXME Resolved but can be optimised
        ArrayList<CollectiveObj> collectiveObjs = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("collectives").getChildren()){
            //System.out.println("getCollectivesList" + collectiveObj.getCollectiveId()); //FIXME Sucessfully prints the 3 collective ID's on the DB
            collectiveObjs.add(snapshot.getValue(CollectiveObj.class)); // FIXME: 24/02/18 throwing issues adding transactions now!
        } //FIXME IDEA: Only initialise the collectiveObjects the user has - i.e. check the i.d. of the collective you are on in the snapshot and if = to one in the usercollective array initaise it
        // might have to change to only select the necessary collectiveObj attributes and leave the rest
        return collectiveObjs;
    }

    public boolean checkGPlay() {
        GoogleApiAvailability apiAva = GoogleApiAvailability.getInstance();
        int curVer = apiAva.isGooglePlayServicesAvailable(this);
        if (curVer != ConnectionResult.SUCCESS) {
            if (apiAva.isUserResolvableError(curVer)) {
                apiAva.getErrorDialog(this, curVer, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Toast.makeText(HomeCollectiveView.this,"Your device does not support Google Push Notifications. Please update your OS", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "This device is not supported. Play services cannot be updated");
                finish();
            }
            return false; //if if is true
        }
        return true;
    }

    private class CollectiveAdaptor extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflator;
        private ArrayList<CollectiveObj> mDataSource;

        public CollectiveAdaptor(Context context, ArrayList<CollectiveObj> collectiveList){
            mContext = context;
            mDataSource = collectiveList;
            mInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount(){
            return mDataSource.size();
        }

        @Override
        public CollectiveObj getItem(int position){
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position){
            return mDataSource.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View rowView = mInflator.inflate(R.layout.list_item_recipe, parent, false);
            TextView titleTextView = (TextView) rowView.findViewById(R.id.recipe_list_title); // title element
            TextView subtitleTextView = (TextView) rowView.findViewById(R.id.recipe_list_subtitle); // subtitle element
            TextView detailTextView = (TextView) rowView.findViewById(R.id.recipe_list_detail);
            ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.recipe_list_thumbnail); // thumbnail element

            CollectiveObj collective = getItem(position);

            titleTextView.setText(collective.getCollectiveName());
            subtitleTextView.setText(collective.getCollectiveType());
            detailTextView.setText(collective.getCollectiveId());

            Picasso.with(mContext).load("http://developer.android.com/studio/images/studio-icon.png").placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
            return rowView;
        }

    }
   /* private ServiceConnection myConnect = new ServiceConnection() { //creating the binded service
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyLocalBinder myBinder = (MyLocalBinder) service;
            myServ = myBinder.getService();
            isBound = true;
        }@Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };*/

}
