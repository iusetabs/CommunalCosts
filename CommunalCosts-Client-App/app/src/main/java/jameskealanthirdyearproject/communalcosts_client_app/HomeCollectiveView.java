package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeCollectiveView extends AppCompatActivity implements View.OnClickListener {

    private ListView joinedCollectivesView;
    private CollectiveAdaptor adaptor;
    private FloatingActionButton addCollectiveButton;
    private Intent addCollective, logInActivity, testActivity;
    private Button logOutBtn, testBtn; //testbutton added by james
    private FirebaseAuth firAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_collective_view);
        ArrayList<String> members = new ArrayList<>();
        members.add("john");

        CollectiveObj collective1 = new CollectiveObj("kilmore road", "house", "uid", "kealan", members );
        CollectiveObj collective2 = new CollectiveObj("shanwoen square","apartment","uid2","kealan", members);

        final ArrayList<CollectiveObj> collectiveList = new ArrayList<>();

        collectiveList.add(collective1);
        collectiveList.add(collective2);

        joinedCollectivesView = (ListView) findViewById(R.id.collectiveListView);
        adaptor = new CollectiveAdaptor(HomeCollectiveView.this, collectiveList);
        joinedCollectivesView.setAdapter(adaptor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addCollective = new Intent(HomeCollectiveView.this, CreateNewCollectiveActivity.class);
        testActivity = new Intent(HomeCollectiveView.this, TestingTransactionAddition.class); //added by james
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectiveObj selectedCollective = collectiveList.get(position);
                Intent detailIntent = new Intent(HomeCollectiveView.this, CollectiveViewActivity.class);
/*                detailIntent.putExtra("CURRENT_COLLECTIVE_ID", selectedCollective.getCollectiveId());
                Log.d("*** OUTBOUND INTENT: ", "" + detailIntent.getExtras().get("CURRENT_COLLECTIVE_ID"));*/
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
