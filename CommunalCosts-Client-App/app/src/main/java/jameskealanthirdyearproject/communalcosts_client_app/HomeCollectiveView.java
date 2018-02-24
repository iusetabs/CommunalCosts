package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class HomeCollectiveView extends AppCompatActivity implements View.OnClickListener {

    private ListView joinedCollectivesView;
    private CollectiveAdaptor adaptor;
    private FloatingActionButton addCollectiveButton;
    private Intent addCollective, logInActivity, testActivity;
    private Button logOutBtn, testBtn; //testbutton added by james
    private FirebaseAuth firAuth;
    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_collective_view);
        final ArrayList<CollectiveObj> collectiveList = new ArrayList<>();
        joinedCollectivesView = (ListView) findViewById(R.id.collectiveListView);
        adaptor = new CollectiveAdaptor(HomeCollectiveView.this, collectiveList);



        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference(); //FIXME this should only be listening to the users/userID/myCollectives area, presently it's listening to the entire database

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> myCollectives = getMyCollectives(dataSnapshot);
                ArrayList<CollectiveObj> collectiveObjList = getCollectivesList(dataSnapshot); // FIXME: Fixed issue was the Database
                for(CollectiveObj collectiveObj : collectiveObjList){
                    if(myCollectives.contains(collectiveObj.getCollectiveId())){
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

        testBtn = (Button) findViewById(R.id.home_testBtn); //added by james
        testBtn.setOnClickListener(this);

        logOutBtn = (Button) findViewById(R.id.logoutBtn);
        logOutBtn.setOnClickListener(this);

        firAuth = FirebaseAuth.getInstance();
        logInActivity = new Intent(HomeCollectiveView.this, LogInActivity.class);
        // adding click functionality
        final Context context = this;

        joinedCollectivesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //FIXME
                CollectiveObj selectedCollective = collectiveList.get(position);
                Intent detailIntent = new Intent(HomeCollectiveView.this, CollectiveViewActivity.class);
                detailIntent.putExtra("CURRENT_COLLECTIVE_ID", selectedCollective.getCollectiveId());
                //Log.d("*** OUTBOUND INTENT: ", "" + detailIntent.getExtras().get("CURRENT_COLLECTIVE_ID"));*/
                System.out.print("Intent section being hit");
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == addCollectiveButton) {
            finish();
            startActivity(addCollective);
        }
        else if (v == logOutBtn){
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
        FirebaseUser userRef = firAuth.getCurrentUser();
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
            collectiveObjs.add(snapshot.getValue(CollectiveObj.class));
        } //FIXME IDEA: Only initialise the collectiveObjects the user has - i.e. check the i.d. of the collective you are on in the snapshot and if = to one in the usercollective array initaise it
        return collectiveObjs;
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
}
